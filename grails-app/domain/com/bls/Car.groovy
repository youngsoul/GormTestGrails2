package com.bls

class Car {

  String name
  Engine engine  // note in the 'hasOne' relationship the Engine must be saved prior to Car
                  // and the engine will not be deleted when the car is deleted.
                  // Car will cascade saves to Engine


  static constraints = {
    engine(nullable: true)
  }
//  static mapping = {
//    tablePerHierarchy(false)
//    //engine fetch: 'join' --to always load an engine when a car is loaded
//  }
}
