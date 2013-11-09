package it.f2informatica.webapp.listeners;

import com.google.common.collect.Lists;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.List;

public class SessionLocalesAggregatorListener implements HttpSessionListener {
	private static final Logger log = Logger.getLogger(SessionLocalesAggregatorListener.class);
	public static final List<String> LANGUAGES = Lists.newArrayList("en", "it");

	@Override
	public void sessionCreated(HttpSessionEvent sessionEvent) {
		sessionEvent.getSession().setAttribute("languages", LANGUAGES);
		log.info("Loaded languages -> " + LANGUAGES);
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent sessionEvent) {
		sessionEvent.getSession().removeAttribute("languages");
		log.info("Removed languages -> " + LANGUAGES);
	}

}
