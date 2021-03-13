package o1.adventure

import scala.collection.mutable.Map


/** A `Player` object represents a player character controlled by the real-life user of the program.
  *
  * A player object's state is mutable: the player's location and possessions can change, for instance.
  *
  * @param startingArea  the initial location of the player */
class Player(startingArea: Area) {

  private var currentLocation = startingArea        // gatherer: changes in relation to the previous location
  private var quitCommandGiven = false              // one-way flag
  private val items = Map[String, Item]()
  private var turns = 10

  val saestaja = new Accompanist(startingArea)

  def remainingTurns = this.turns

  def currentTurns() = {
    this.turns -= 1
  }

  def vuorot = {
    "Sinulla on jäljellä " + (this.remainingTurns - 1) + " vuoroa."
  }

  def accompanistLocation() = {
    if (this.turns % 3 == 0) {
      this.saestaja.liiku()
    }
  }

  /** Ilmoittaa säestäjän tämänhetkisen olinpaikan. */
  def paniikki() = {
    this.turns -= 2
    "Säestäjäsi olinpaikka on tällä hetkellä " + this.saestaja.location.name
  }

  def ota(itemName:String) = {
    if (this.currentLocation.contains(itemName)) {
      val itemOpt = this.currentLocation.removeItem(itemName)
      val item = itemOpt match {
        case Some(someItem) => someItem
      }
      this.items(itemName) = item
      "Jee, siinäpä sinulle " + itemName + "."
    } else {
      "Voih, " + itemName + " ei ole täällä."
    }
  }

  def tutki(itemName: String) = {
    if (this.items.contains(itemName)) {
      val itemOpt = this.items.get(itemName)
      val item = itemOpt match {
        case Some(someItem) => someItem
      }
      "Tarkastelusi kohteena on " + itemName + ".\n" + item.description
    } else {
      "Et voi tutkia mielikuvitusesineitä. Täytyy etsiä oikeita."
    }
  }

  def pudota(itemName: String) = {
    if (this.items.contains(itemName)) {
      val itemOpt = this.items.remove(itemName)
      val item = itemOpt match {
        case Some(someItem) => someItem
      }
      this.currentLocation.addItem(item)
      "Jotain putosi, se oli " + item + "."
    } else {
      "Ei löydy repustasi!"
    }
  }

  def has(itemName: String) = this.items.contains(itemName)

  /** Kertoo mitä pelaajalla on hallussaan. */
  def inventaario = {
    if (this.items.isEmpty) {
      "Nuottisi ja vaatteesi ovat edelleen kateissa"
    } else {
      "Hallustasi löytyy:\n" + this.items.keys.mkString("\n")
    }
  }

  /** Determines if the player has indicated a desire to quit the game. */
  def hasQuit = this.quitCommandGiven


  /** Returns the current location of the player. */
  def location = this.currentLocation


  /** Attempts to move the player in the given direction. This is successful if there
    * is an exit from the player's current location towards the direction name. Returns
    * a description of the result: "You go DIRECTION." or "You can't go DIRECTION." */
  def mene(direction: String) = {
    val destination = this.location.neighbor(direction)
    this.currentLocation = destination.getOrElse(this.currentLocation)
    if (destination.isDefined) "Kuljet " + direction + "." else "Et voi kulkea " + direction + "."
  }


  /** Causes the player to rest for a short while (this has no substantial effect in game terms).
    * Returns a description of what happened. */
  def huilaa() = {
    "Lepäät hetken. Kannattaa kuitenkin pitää kiirettä, koska esiintymisvuorosi lähestyy."
  }

  /** Saa pelaajan syömään välipalan, joka lisää toimintavuoroja viidellä. */
  def haukkaa(itemName: String) = {
    if ((this.currentLocation.contains("suklaakonvehti") || this.currentLocation.contains("rahkapulla") || this.currentLocation.contains("nallekarkki")) &&
      (itemName == "suklaakonvehti" || itemName == "nallekarkki" || itemName == "rahkapulla")) {
      this.turns += 5
      this.currentLocation.removeItem(itemName)
      "Nyt jaksaa taas!"
    } else if ((this.currentLocation.contains("suklaakonvehti") || this.currentLocation.contains("rahkapulla") || this.currentLocation.contains("nallekarkki")) &&
      !(itemName == "suklaakonvehti" || itemName == "nallekarkki" || itemName == "rahkapulla"))  {
      "Ei löydy sellaista herkkua!"
    } else {
      "Täältä ei löydy syömistä."
    }
  }

  /** Kysyy säestäjältä neuvoa löytääkseen oikean nuotin. */
  def kysy() = {
    if (this.saestaja.location == this.currentLocation) {
      "Tänään on impressionismi-ilta. Soitetaan siis Debussya."
    } else {
      "Säestäjäsi on juuri nyt jossain muualla."
    }
  }

  /** Kun pelaajalla on hallussaan oikea nuotti, päällään esiintymisvaatteet ja hän on konserttisalissa, tämä käsky saa hänet esittämään kappaleensa. */
  def esiinny() = {
    if (this.items.contains("debussy-nuotit") && this.items.contains("konserttivaatteet") && this.currentLocation.name == "Konserttisali") {
      val ruusu = new Item("ruusu", "Ihana punainen ruusu!")
      this.items("ruusu") = ruusu
      "Olet ansainnut ruusun hienosta esityksestäsi!"
    } else {
      "Et ole vielä valmis esiintymään! Etsi nuotit ja vaihda vaatteet!"
    }
  }


  /** Signals that the player wants to quit the game. Returns a description of what happened within
    * the game as a result (which is the empty string, in this case). */
  def lopeta() = {
    this.quitCommandGiven = true
    ""
  }


  /** Returns a brief description of the player's state, for debugging purposes. */
  override def toString = "Tämänhetkinen sijaintisi opistolla on: " + this.location.name


}


