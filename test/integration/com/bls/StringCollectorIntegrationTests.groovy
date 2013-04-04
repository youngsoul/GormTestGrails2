package com.bls

import static org.junit.Assert.*
import org.junit.*

class StringCollectorIntegrationTests {

  @Before
  void setUp() {
    // Setup logic here
  }

  @After
  void tearDown() {
    // Tear down logic here
  }

  @Test
  void testSomething() {
    StringCollector stringCollector = new StringCollector(name: "name1")
    stringCollector.addToMyStrings("myString1")
    stringCollector.addToMyStrings("myString2")
    stringCollector.addToMyStrings("myString3")

    println "============ 1 stringCollector.save ======="
    stringCollector.save(flush: true, failOnError: true)

    println "============ 2 call clear on the myStrings ======="
    stringCollector.myStrings.clear()

    println "============ 3 call save on stringCollector ======="
    stringCollector.save(flush: true, failOnError: true)

    println "============  4 call clear on already clear the myStrings ======="
    stringCollector.myStrings.clear()

    println "============ 5 call save on stringCollector ======="
    stringCollector.save(flush: true, failOnError: true)

    println "============  6 set empty myStrings ======="
    stringCollector.myStrings = []

    println "============ 7 call clear on already clear the myStrings ======="
    stringCollector.myStrings.clear()

    println "============  8 call save on stringCollector ======="
    stringCollector.save(flush: true, failOnError: true)

    println "============  9 findById stringCollector ======="
    StringCollector stringCollector1 = StringCollector.findByName("name1")

    println "============ 10 call clear on already clear the myStrings ======="
    stringCollector1.name = "name2"
    stringCollector1.myStrings = []
    stringCollector1.myStrings.clear()

    println "============ 11 call save on stringCollector ======="
    stringCollector1.save(flush: true, failOnError: true)




  }
}
