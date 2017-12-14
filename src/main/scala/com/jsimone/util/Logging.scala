package com.jsimone.util

import org.apache.log4j.LogManager

trait Logging {
  def log = LogManager.getLogger(getClass)
}