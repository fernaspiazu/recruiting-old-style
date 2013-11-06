package it.f2informatica.webapp;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.webapp.WebAppContext;

import java.io.File;
import java.security.ProtectionDomain;

public class RecruitingMain {
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
		ServerConnector httpConnector = new ServerConnector(server);
		httpConnector.setHost("localhost");
		httpConnector.setPort(defaultPort());
		httpConnector.setIdleTimeout(30000);
		httpConnector.setAcceptQueueSize(200);
		server.addConnector(httpConnector);
		return server;
	}

	private static int defaultPort() {
		return IS_PROCESSING ? 8081 : 8080;
	}

	private static String getWarBase() {
		if (new File(LOCAL_WAR_DIR).exists()) {
			return LOCAL_WAR_DIR;
		}
		ProtectionDomain domain = RecruitingMain.class.getProtectionDomain();
		return domain.getCodeSource().getLocation().toString();
	}

}
