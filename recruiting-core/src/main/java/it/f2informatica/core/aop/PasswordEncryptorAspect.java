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
package it.f2informatica.core.aop;

import it.f2informatica.core.model.UpdatePasswordModel;
import it.f2informatica.core.model.UserModel;
import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class PasswordEncryptorAspect {
	private static Logger logger = Logger.getLogger(PasswordEncryptorAspect.class);

	@Autowired
	private BCryptPasswordEncoder encoder;

	@Before("setPasswordBeforeSave() || setPasswordBeforeUpdate()")
	public void encryptPasswordAdvice(JoinPoint joinPoint) {
		logger.debug("Encrypting password while saving|updating...");
		UserModel user = (UserModel) joinPoint.getArgs()[0];
		String passwordEncoded = encoder.encode(user.getPassword());
		user.setPassword(passwordEncoded);
		logger.debug("Password encoded [" + passwordEncoded + "]");
	}

	@Pointcut("execution(public * it.f2informatica.core.services.UserService.saveUser(..))")
	private void setPasswordBeforeSave() {}

	@Pointcut("execution(public * it.f2informatica.core.services.UserService.updateUser(..))")
	private void setPasswordBeforeUpdate() {}

	@Before("setCurrentPassword()")
	public void encryptPasswordBeforeUpdate(JoinPoint joinPoint) {
		UpdatePasswordModel model = (UpdatePasswordModel) joinPoint.getArgs()[0];
		model.setCurrentPassword(encoder.encode(model.getCurrentPassword()));
	}

	@Pointcut("execution(public * it.f2informatica.core.validator.UpdatePasswordModelValidator.validate(..))")
	private void setCurrentPassword() {}

}
