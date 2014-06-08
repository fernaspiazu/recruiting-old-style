/*
 * =============================================================================
 *
 *   Copyright (c) 2014, Fernando Aspiazu
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 * =============================================================================
 */
package it.f2informatica.webapp.server;

import org.eclipse.jetty.deploy.DeploymentManager;
import org.eclipse.jetty.deploy.providers.WebAppProvider;
import org.eclipse.jetty.jmx.MBeanContainer;
import org.eclipse.jetty.security.HashLoginService;
import org.eclipse.jetty.server.*;
import org.eclipse.jetty.server.handler.*;
import org.eclipse.jetty.spdy.server.NPNServerConnectionFactory;
import org.eclipse.jetty.spdy.server.SPDYServerConnectionFactory;
import org.eclipse.jetty.spdy.server.http.HTTPSPDYServerConnectionFactory;
import org.eclipse.jetty.spdy.server.http.PushStrategy;
import org.eclipse.jetty.spdy.server.http.ReferrerPushStrategy;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.eclipse.jetty.util.thread.QueuedThreadPool;

import java.lang.management.ManagementFactory;

public class SpdyServer {
  private static final String JETTY_HOME = System.getProperty("jetty.home");
  private static final String WEBAPP_TARGET = System.getProperty("webapp.target");

  public static void main(String[] args) throws Exception {
    Server server = setupJettyServerInstance();
    MBeanContainer mbContainer = new MBeanContainer(ManagementFactory.getPlatformMBeanServer());
    server.addBean(mbContainer);

    HttpConfiguration config = setupCommonHTTPConfiguration();
    HttpConnectionFactory http = setupHttpConnection(server, config);
    SslContextFactory sslContextFactory = sslConfiguration();


    // Spdy Connector
    // Make sure that the required NPN implementations are available.
    SPDYServerConnectionFactory.checkProtocolNegotiationAvailable();

    // A ReferrerPushStrategy is being initialized.
    // See: http://www.eclipse.org/jetty/documentation/current/spdy-configuring-push.html for more details.
    PushStrategy push = new ReferrerPushStrategy();
    HTTPSPDYServerConnectionFactory spdy2 = new HTTPSPDYServerConnectionFactory(2,config,push);
    spdy2.setInputBufferSize(8192);
    spdy2.setInitialWindowSize(32768);

    // We need a connection factory per protocol that our server is supposed to support on the NPN port. We then
    // create a ServerConnector and pass in the supported factories. NPN will then be used to negotiate the
    // protocol with the client.
    HTTPSPDYServerConnectionFactory spdy3 = new HTTPSPDYServerConnectionFactory(3,config,push);
    spdy2.setInputBufferSize(8192);

    NPNServerConnectionFactory npn = new NPNServerConnectionFactory(spdy3.getProtocol(),spdy2.getProtocol(),http.getProtocol());
    npn.setDefaultProtocol(http.getProtocol());
    npn.setInputBufferSize(1024);

    SslConnectionFactory ssl = new SslConnectionFactory(sslContextFactory,npn.getProtocol());

    // Setup the npn connector on port 8443
    ServerConnector spdyConnector = new ServerConnector(server,ssl,npn,spdy3,spdy2,http);
    spdyConnector.setPort(8443);

    server.addConnector(spdyConnector);

    // Setup handlers
    HandlerCollection handlers = new HandlerCollection();
    ContextHandlerCollection contexts = new ContextHandlerCollection();
    RequestLogHandler requestLogHandler = new RequestLogHandler();

    handlers.setHandlers(new Handler[] { contexts, new DefaultHandler(), requestLogHandler });

    StatisticsHandler stats = new StatisticsHandler();
    stats.setHandler(handlers);

    server.setHandler(stats);

    // Setup deployers
    DeploymentManager deployer = new DeploymentManager();
    deployer.setContexts(contexts);
    server.addBean(deployer);

    WebAppProvider webapp_provider = new WebAppProvider();
    webapp_provider.setMonitoredDirName(WEBAPP_TARGET + "/webapps");
    webapp_provider.setParentLoaderPriority(false);
    webapp_provider.setExtractWars(true);
    webapp_provider.setScanInterval(2);
    //webapp_provider.setDefaultsDescriptor(jetty_home + "/etc/webdefault.xml");
    deployer.addAppProvider(webapp_provider);

    HashLoginService login = new HashLoginService();
    login.setName("Test Realm");
    login.setConfig(JETTY_HOME + "/realm.properties");
    server.addBean(login);

    NCSARequestLog requestLog = new AsyncNCSARequestLog();
    requestLog.setFilename(WEBAPP_TARGET + "/jetty-yyyy_mm_dd.log");
    requestLog.setExtended(false);
    requestLogHandler.setRequestLog(requestLog);

    server.setStopAtShutdown(true);

    server.start();
    server.dumpStdErr();
    server.join();
  }

  private static Server setupJettyServerInstance() {
    // Setup Threadpool
    QueuedThreadPool threadPool = new QueuedThreadPool(512);

    Server server = new Server(threadPool);
    server.manage(threadPool);
    server.setDumpAfterStart(false);
    server.setDumpBeforeStop(false);
    return server;
  }

  private static HttpConfiguration setupCommonHTTPConfiguration() {
    HttpConfiguration config = new HttpConfiguration();
    config.setSecurePort(8443);
    config.addCustomizer(new ForwardedRequestCustomizer());
    config.addCustomizer(new SecureRequestCustomizer());
    config.setSendServerVersion(true);
    return config;
  }

  /**
   * Http Connector Setup
   * A plain HTTP connector listening on port 8080. Note that it's also possible to have port 8080 configured as
   * a non SSL SPDY connector. But the specification and most browsers do not allow to use SPDY without SSL
   * encryption. However some browsers allow it to be configured.
   */
  private static HttpConnectionFactory setupHttpConnection(Server server, HttpConfiguration config) {
    HttpConnectionFactory http = new HttpConnectionFactory(config);
    ServerConnector httpConnector = new ServerConnector(server,http);
    httpConnector.setPort(8080);
    httpConnector.setIdleTimeout(10000);
    server.addConnector(httpConnector);
    return http;
  }

  /**
   * SSL configurations
   *
   * We need a SSLContextFactory for the SSL encryption.
   * That SSLContextFactory will be used by the SPDY connector.
   */
  private static SslContextFactory sslConfiguration() {
    SslContextFactory sslContextFactory = new SslContextFactory();
    sslContextFactory.setKeyStorePath(JETTY_HOME + "/keystore");
    sslContextFactory.setKeyStorePassword("OBF:1vny1zlo1x8e1vnw1vn61x8g1zlu1vn4");
    sslContextFactory.setKeyManagerPassword("OBF:1u2u1wml1z7s1z7a1wnl1u2g");
    sslContextFactory.setTrustStorePath(JETTY_HOME + "/keystore");
    sslContextFactory.setTrustStorePassword("OBF:1vny1zlo1x8e1vnw1vn61x8g1zlu1vn4");
    sslContextFactory.setExcludeCipherSuites(
      "SSL_RSA_WITH_DES_CBC_SHA",
      "SSL_DHE_RSA_WITH_DES_CBC_SHA",
      "SSL_DHE_DSS_WITH_DES_CBC_SHA",
      "SSL_RSA_EXPORT_WITH_RC4_40_MD5",
      "SSL_RSA_EXPORT_WITH_DES40_CBC_SHA",
      "SSL_DHE_RSA_EXPORT_WITH_DES40_CBC_SHA",
      "SSL_DHE_DSS_EXPORT_WITH_DES40_CBC_SHA");
    return sslContextFactory;
  }

}
