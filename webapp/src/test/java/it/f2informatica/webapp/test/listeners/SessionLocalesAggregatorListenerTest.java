package it.f2informatica.webapp.test.listeners;

import it.f2informatica.webapp.listener.SessionLocalesAggregatorListener;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.mock.web.MockHttpSession;

import javax.servlet.http.HttpSessionEvent;
import java.util.List;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SessionLocalesAggregatorListenerTest {

	@Mock
	HttpSessionEvent sessionEvent;

	MockHttpSession httpSession = new MockHttpSession();

	SessionLocalesAggregatorListener sessionLocalesAggregatorListener;

	@Before
	public void setUp() {
		sessionLocalesAggregatorListener = new SessionLocalesAggregatorListener();
		when(sessionEvent.getSession()).thenReturn(httpSession);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void sessionCreated() {
		sessionLocalesAggregatorListener.sessionCreated(sessionEvent);
		List<String> languages = (List<String>) httpSession.getAttribute("languages");
		assertThat(languages).hasSize(2).contains("en");
	}

	@Test
	public void sessionDestroyed() {
		sessionLocalesAggregatorListener.sessionDestroyed(sessionEvent);
		assertThat(httpSession.getAttributeNames().hasMoreElements()).isFalse();
	}

}
