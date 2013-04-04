package com.bls

import static org.junit.Assert.*
import org.junit.*

class AssetTestIntegrationTests {

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
    AssetTest assetTest = new AssetTest(createdByUserApId: "a234234")
    assetTest.comment = "test"
    assetTest.save(flush: true)
    assertNotNull(assetTest)

    println "=======  findByComment  ==========="
    AssetTest assetTestFetch = AssetTest.findByComment("test")
    assertNotNull(assetTestFetch)
    println "================================"
    assetTestFetch.conceptApIds =[]
    assetTestFetch.conceptApIds.clear()
    assetTestFetch.save(flush: true)  }
}
