package com.jsimone.util

import org.apache.logging.log4j.LogManager

trait Logging {
  def log = LogManager.getLogger(getClass)
}