package o1.adventure

class Accompanist(startingArea: Area) {

  private var currentLocation = startingArea

  def location = this.currentLocation

  def liiku() = {
    val roomOption = this.currentLocation.neighbor("vasemmalle")
    val newLocation = roomOption match {
      case Some(someRoom) => someRoom
    }
    this.currentLocation = newLocation
  }

}
