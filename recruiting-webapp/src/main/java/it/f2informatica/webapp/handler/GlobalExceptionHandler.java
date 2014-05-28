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
package it.f2informatica.webapp.handler;

import it.f2informatica.core.exception.PageNotFoundException;
import org.apache.log4j.Logger;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import java.io.PrintWriter;
import java.io.StringWriter;

@ControllerAdvice("it.f2informatica.webapp.controller")
public class GlobalExceptionHandler {
  private static Logger logger = Logger.getLogger(GlobalExceptionHandler.class);

  @ExceptionHandler(NullPointerException.class)
  public ModelAndView nullPointerExceptionHandler(NullPointerException e) {
    logger.error("NullPointerException has been thrown.", e);
    return new ModelAndView(Pages.PAGE_NOT_FOUND);
  }

  @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
  public ModelAndView httpRequestMethodNotSupportedExceptionHandler(HttpRequestMethodNotSupportedException e) {
    logger.error("HttpRequestMethodNotSupportedException has been thrown.", e);
    return new ModelAndView(Pages.PAGE_NOT_FOUND);
  }

  @ExceptionHandler(PageNotFoundException.class)
  public ModelAndView pageNotFoundExceptionHandler(PageNotFoundException e) {
    logger.error("PageNotFoundException has been thrown.", e);
    return new ModelAndView(Pages.PAGE_NOT_FOUND);
  }

  @ExceptionHandler(Exception.class)
  public ModelAndView exceptionHandler(Exception e) {
    logger.error("Some Exception has been thrown.", e);
    StringWriter stringWriter = new StringWriter();
    e.printStackTrace(new PrintWriter(stringWriter));
    return new ModelAndView(Pages.SERVER_ERROR, "stackTrace", stringWriter.toString());
  }

}
