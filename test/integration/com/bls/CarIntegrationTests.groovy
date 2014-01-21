package com.bls

import org.hibernate.SessionFactory

class CarIntegrationTests extends GroovyTestCase {

  SessionFactory sessionFactory
  Boolean flushNow = false
  
  protected void setUp() {
    super.setUp()
  }

  protected void tearDown() {
    super.tearDown()
  }

  void testMustHaveEngineFirst() {
    shouldFail {
      Engine e0 = new Engine(name:  'bad', numberCylinders: 0)
      Car nova = new Car(name: 'nova', engine: e0)
      assertNotNull(nova.save(flush: true, failOnError: true))
    }
  }
  void testCars() {


    Engine e1 = new Engine(name: "dog", numberCylinders: 4)
    e1.save(flush: flushNow)
    Car pinto = new Car(name: 'pinto', engine: e1)
    assertNotNull(pinto.save(flush: flushNow))
    
    assertEquals(1, Engine.count())
    assertEquals(1, Car.count())

    sessionFactory.currentSession.flush()
    sessionFactory.currentSession.clear()
    
    println "========== Car.findByName Lazy Engine ==================="
    Car pinto2 = Car.findByName('pinto')  // Engine will be lazy loaded only on request. must
                                          // change fetch strategy if you want it eagerly loaded
    assertNotNull pinto2
    assertEquals 'pinto', pinto2.name
    println "========== Access Lazy Engine ==================="
    assertEquals 'dog',pinto2.engine.name


    sessionFactory.currentSession.flush()
    sessionFactory.currentSession.clear()

    println "========== Car.findByName Join Engine ==================="
    Car pinto3 = Car.findByName('pinto',[fetch:[engine: "join"]])  // Engine will be eagerly loaded
    // change fetch strategy if you want it eagerly loaded
    assertNotNull pinto3
    assertEquals 'pinto', pinto3.name
    println "========== Access Joined Engine ==================="
    assertEquals 'dog',pinto3.engine.name


    println "========== Car.delete ==================="
    pinto3.delete(flush:flushNow) // notice the engine does not go with it

    sessionFactory.currentSession.flush()
    sessionFactory.currentSession.clear()

    assertEquals(1, Engine.count())
    assertEquals(0, Car.count())

  }

  void testElectricCar() {
    Engine e1 = new Engine(name: "eel", numberCylinders: 0)
    e1.save()
    ElectricCar volt = new ElectricCar(name: 'volt', engine: e1,voltAmps: 200)
    assertNotNull(volt.save(flush: true))

    assertEquals(1, Engine.count())
    assertEquals(1, Car.count())

    sessionFactory.currentSession.flush()
    sessionFactory.currentSession.clear()

    Car volt2 = Car.findByName('volt')  // Engine will be lazy loaded only on request. must
    // change fetch strategy if you want it eagerly loaded
    assertNotNull volt2
    assertEquals 'volt', volt2.name

    volt2.delete(flush:true)

    sessionFactory.currentSession.flush()
    sessionFactory.currentSession.clear()

    assertEquals(1, Engine.count())
    assertEquals(0, Car.count())

  }

  void testFindAllEngines() {

    new Engine(name: "eel", numberCylinders: 0).save(flush: true)
    new Engine(name: "eel", numberCylinders: 3).save(flush: true)
    new Engine(name: "eel", numberCylinders: 6).save(flush: true)
    new Engine(name: "eel", numberCylinders: 9).save(flush: true)
    new Engine(name: "eel", numberCylinders: 2).save(flush: true)

    assertEquals(5, Engine.count())
    
    def allEngines = Engine.findAllByName("eel",[sort: 'numberCylinders', order: 'asc'])

    assertEquals(5, allEngines.size())

  }

  void testDeleteEngineFromCar() {

    Engine e1 = new Engine(name: "dog", numberCylinders: 4)
    e1.save()
    Car pinto = new Car(name: 'pinto', engine: e1)
    assertNotNull(pinto.save(flush: true))

    assertEquals(1, Engine.count())
    assertEquals(1, Car.count())

    sessionFactory.currentSession.flush()
    sessionFactory.currentSession.clear()

    println "========== Car.findByName Lazy Engine ==================="
    Car pinto2 = Car.findByName('pinto')  // Engine will be lazy loaded only on request. must
    // change fetch strategy if you want it eagerly loaded
    assertNotNull pinto2
    assertEquals 'pinto', pinto2.name
    println "========== Access Lazy Engine ==================="
    assertEquals 'dog',pinto2.engine.name

    pinto2.engine.name = "dog2"
    println "========== Update Engine name and save via Car ==================="
    assertNotNull pinto2.save(flush:  true)

    sessionFactory.currentSession.flush()
    sessionFactory.currentSession.clear()

    println "========== Car.findByName Join Engine ==================="
    Car pinto3 = Car.findByName('pinto',[fetch:[engine: "join"]])  // Engine will be eagerly loaded
    // change fetch strategy if you want it eagerly loaded
    assertNotNull pinto3
    assertEquals 'pinto', pinto3.name
    println "========== Access Joined Engine ==================="
    assertEquals 'dog2',pinto3.engine.name


    println "========== Null engine in car, save car ==================="
    Engine e2 = pinto3.engine
    pinto3.engine = null
    pinto3.save(flush:true)

    println "========== Engine.delete ==================="
    e2.delete(flush: true)

    sessionFactory.currentSession.flush()
    sessionFactory.currentSession.clear()

    assertEquals(0, Engine.count())
    assertEquals(1, Car.count())

  }

  void testDeleteEngineInBeforeDeleteHandler() {

    Engine e1 = new Engine(name: "dog", numberCylinders: 4, discontinued: Boolean.TRUE)
    e1.save()
    Car pinto = new Car(name: 'pinto', engine: e1)
    assertNotNull(pinto.save(flush: true))

    assertEquals(1, Engine.count())
    assertEquals(1, Car.count())

    sessionFactory.currentSession.flush()
    sessionFactory.currentSession.clear()

    println "========== Car Delete ==================="
    pinto.delete(flush: true)

    sessionFactory.currentSession.flush()
    sessionFactory.currentSession.clear()

    assertEquals(0, Engine.count())
    assertEquals(0, Car.count())

  }

}
