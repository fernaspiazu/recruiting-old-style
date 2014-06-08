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

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.webapp.WebAppContext;

import java.io.File;
import java.security.ProtectionDomain;

public class HttpServer {
  private static final boolean IS_PROCESSING = Boolean.parseBoolean(System.getProperty("processing.config"));
  private static final String LOCAL_WAR_DIR = "./src/main/webapp";

  public static void main(String[] args) throws Exception {
    Server server = createServer();
    WebAppContext webAppContext = new WebAppContext(getWarBase(), "/recruiting");
    webAppContext.setServer(server);
    server.setHandler(webAppContext);
    server.start();
    server.join();
  }

  private static Server createServer() {
    Server server = new Server();
    ServerConnector httpServerConnector = new ServerConnector(server);
    httpServerConnector.setHost("localhost");
    httpServerConnector.setPort(defaultPort());
    httpServerConnector.setIdleTimeout(30000);
    httpServerConnector.setAcceptQueueSize(200);
    server.addConnector(httpServerConnector);
    return server;
  }

  private static int defaultPort() {
    return IS_PROCESSING ? 8081 : 8080;
  }

  private static String getWarBase() {
    if (new File(LOCAL_WAR_DIR).exists()) {
      return LOCAL_WAR_DIR;
    }
    ProtectionDomain domain = HttpServer.class.getProtectionDomain();
    return domain.getCodeSource().getLocation().toString();
  }

}
