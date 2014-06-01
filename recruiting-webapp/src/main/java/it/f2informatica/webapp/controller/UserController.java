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
package it.f2informatica.webapp.controller;

import com.google.common.base.Optional;
import it.f2informatica.core.model.UpdatePasswordModel;
import it.f2informatica.core.model.UserModel;
import it.f2informatica.core.services.PasswordUpdaterService;
import it.f2informatica.core.services.UserService;
import it.f2informatica.core.validator.UpdatePasswordModelValidator;
import it.f2informatica.core.validator.UserModelValidator;
import it.f2informatica.core.validator.utils.ValidationResponse;
import it.f2informatica.core.validator.utils.ValidationResponseHandler;
import it.f2informatica.pagination.services.QueryParameters;
import it.f2informatica.webapp.security.SecurityAccessor;
import it.f2informatica.webapp.utils.CurrentHttpRequest;
import it.f2informatica.webapp.utils.HttpRequestQueryParameters;
import it.f2informatica.webapp.utils.MediaTypeUTF8;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
public class UserController {

  @Autowired
  private UserService userService;

  @Autowired
  private CurrentHttpRequest httpRequest;

  @Autowired
  private SecurityAccessor securityAccessor;

  @Autowired
  private UserModelValidator userModelValidator;

  @Autowired
  private PasswordUpdaterService passwordUpdaterService;

  @Autowired
  private ValidationResponseHandler validationResponseHandler;

  @Autowired
  private UpdatePasswordModelValidator updatePasswordModelValidator;

  @RequestMapping(value = "/load-users", method = RequestMethod.GET, produces = MediaTypeUTF8.JSON_UTF_8)
  public @ResponseBody String loadUsers() {
    QueryParameters parameters = new HttpRequestQueryParameters(httpRequest.getCurrentHttpRequest());
    return userService.getAllUsersPaginated(parameters, securityAccessor.getCurrentUsername());
  }

  @RequestMapping(value = "/save", method = RequestMethod.POST)
  public String saveUser(@ModelAttribute("userModel") UserModel userModel) {
    userService.saveUser(userModel);
    return "redirect:/users";
  }

  @RequestMapping(value = "/edit", method = RequestMethod.GET, produces = MediaTypeUTF8.JSON_UTF_8)
  public @ResponseBody UserModel editUser(@RequestParam String userId) {
    return userService.findUserById(userId).orNull();
  }

  @RequestMapping(value = "/update", method = RequestMethod.POST)
  public String updateUser(@ModelAttribute("userModel") UserModel userModel) {
    userService.updateUser(userModel);
    return "redirect:/users";
  }

  @RequestMapping(value = "/delete", method = RequestMethod.GET)
  public String deleteUser(@RequestParam String userId) {
    userService.deleteUser(userId);
    return "redirect:/users";
  }

  @RequestMapping(value = "/validate-user", method = RequestMethod.POST, produces = MediaTypeUTF8.JSON_UTF_8)
  public @ResponseBody ValidationResponse validateUser(@ModelAttribute("userModel") UserModel userModel, BindingResult result) {
    userModelValidator.validate(userModel, result);
    if (result.hasErrors()) {
      return validationResponseHandler.validationFail(result, httpRequest.getLocale());
    }
    return validationResponseHandler.validationSuccess();
  }

  @RequestMapping(value = "/change-password", method = RequestMethod.GET)
  public String changePasswordForm(@RequestParam("userId") String userId, ModelMap model) {
    Optional<UserModel> user = userService.findUserById(userId);
    if (user.isPresent()) {
      model.addAttribute("readonlyUsername", user.get().getUsername());
      model.addAttribute("changePasswordModel", passwordUpdaterService.prepareUpdatePasswordModel(userId));
      return "user/changePasswordForm";
    }

    return pageNotFound();
  }

  @RequestMapping(value = "/update-password", method = RequestMethod.POST)
  public String updatePassword(@ModelAttribute("changePasswordModel") UpdatePasswordModel updatePasswordModel) {
    passwordUpdaterService.updatePassword(updatePasswordModel);
    return "redirect:/users";
  }

  @RequestMapping(value = "/validate-password-updating", method = RequestMethod.POST, produces = MediaTypeUTF8.JSON_UTF_8)
  public @ResponseBody ValidationResponse validatePasswordUpdating(@ModelAttribute("changePasswordModel") UpdatePasswordModel updatePasswordModel, BindingResult result) {
    updatePasswordModelValidator.validate(updatePasswordModel, result);
    if (result.hasErrors()) {
      return validationResponseHandler.validationFail(result, httpRequest.getLocale());
    }
    return validationResponseHandler.validationSuccess();
  }

  private static String pageNotFound() {
    return "404";
  }

}
