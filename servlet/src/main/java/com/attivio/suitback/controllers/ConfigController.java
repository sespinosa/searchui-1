/**
* Copyright 2018 Attivio Inc., All rights reserved.
*/
package com.attivio.suitback.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@CrossOrigin
@Controller
public class ConfigController {
  // Add the property logging.level.com.attivio.suitback.controllers.ConfigController
  // to the application.properties to get debug logging (e.g., with value of DEBUG).
  static final Logger LOG = LoggerFactory.getLogger(ConfigController.class);

  @Value("${suit.attivio.users:}")
  private String usersFileLocation;

  @Value("${suit.attivio.configuration:}")
  private String configurationFileLocation;

  @ResponseBody
  @RequestMapping("/users")
  public String users() {
    if (usersFileLocation != null && usersFileLocation.length() > 0) {
      try {
        return new String(Files.readAllBytes(Paths.get(usersFileLocation)));
      } catch (IOException e) {
        LOG.info("Failed to load the users file from: " + usersFileLocation, e);
        return "";
      }
    } else {
      LOG.debug("No users location is configured.");
    }
    return "";
  }
  
  @ResponseBody
  @RequestMapping("/configuration")
  public String configuration() {
    try {
      return new String(Files.readAllBytes(Paths.get(configurationFileLocation)));
    } catch (IOException e) {
      LOG.info("Failed to load the configuration file from: " + configurationFileLocation, e);
      return "";
    }
  }
}
