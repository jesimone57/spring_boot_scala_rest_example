package com.jsimone.scheduler

import java.util.Calendar
import java.util.concurrent.TimeUnit

import com.jsimone.util.Logging
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

// Uncomment the next line to allow task scheduling
//@Component
class TaskScheduler extends Logging {

  /**
    * Task will execute at the given rate.
    */
  @Scheduled(fixedRate = 2000)
  def scheduleTaskWithFixedRate() {
    val now = Calendar.getInstance()
    val currentHour = now.get(Calendar.HOUR)
    val currentMinute = now.get(Calendar.MINUTE)
    val currentSecond = now.get(Calendar.SECOND)
    log.info(s"Fixed Rate Scheduled Task executing at $currentHour Hr $currentMinute Min $currentSecond Sec")
  }

  /**
    * Since the task itself takes 5 seconds to complete and we have specified a delay of 2 seconds
    * between the completion of the last invocation and the start of the next,
    * there will be a delay of 7 seconds between each invocation
    *
   */
  @Scheduled(fixedDelay = 2000)
  def scheduleTaskWithFixedDelay(): Unit = {
    val now = Calendar.getInstance()
    val currentHour = now.get(Calendar.HOUR)
    val currentMinute = now.get(Calendar.MINUTE)
    val currentSecond = now.get(Calendar.SECOND)
    TimeUnit.SECONDS.sleep(5)
    log.info(s"Fixed Delay Scheduled Task executing at $currentHour Hr $currentMinute Min $currentSecond Sec")
  }

  /**
    * Task will begin after the initial delay given and will execute at the given rate.
    */
  @Scheduled(fixedRate = 2000, initialDelay = 5000)
  def scheduleTaskWithInitialDelay(): Unit = {
    val now = Calendar.getInstance()
    val currentHour = now.get(Calendar.HOUR)
    val currentMinute = now.get(Calendar.MINUTE)
    val currentSecond = now.get(Calendar.SECOND)
    log.info(s"Fixed Rate with Initial Delay Scheduled Task executing at $currentHour Hr $currentMinute Min $currentSecond Sec")
  }

  /**
    * The Cron time string is five values separated by spaces, based on the following information:
    * Character	Descriptor	Acceptable values
    *   1	Minute	0 to 59, or * (no specific value)
    *   2	Hour	0 to 23, or * for any value. All times UTC.
    *   3	Day of the month	1 to 31, or * (no specific value)
    *   4	Month	1 to 12, or * (no specific value)
    *   5	Day of the week	0 to 7 (0 and 7 both represent Sunday), or * (no specific value)
    *
    * The Cron time string must contain entries for each character attribute.
    * If you want to set a value using only minutes, you must have asterisk characters for
    * the other four attributes that you're not configuring (hour, day of the month, month, and day of the week).
    *
    * Note that entering a value for a character attribute configures a task to run at a regular time.
    * If you append a slash ( / ) and an integer to a wildcard in any of the character positions however,
    * you can configure the cron task to run at a regular interval that isn't dependent
    * on a regular time (for example, every X minutes/hours/days).
    *
    */
  @Scheduled(cron = "0 * * * * ?")
  def scheduleTaskWithCronExpression(): Unit = {
    val now = Calendar.getInstance()
    val currentHour = now.get(Calendar.HOUR)
    val currentMinute = now.get(Calendar.MINUTE)
    val currentSecond = now.get(Calendar.SECOND)
    log.info(s"Cron Scheduled Task executing at $currentHour Hr $currentMinute Min $currentSecond Sec")
  }
}
