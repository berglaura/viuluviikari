package o1.adventure


/** The class `Adventure` represents text adventure games. An adventure consists of a player and
  * a number of areas that make up the game world. It provides methods for playing the game one
  * turn at a time and for checking the state of the game.
  *
  * N.B. This version of the class has a lot of "hard-coded" information which pertain to a very
  * specific adventure game that involves a small trip through a twisted forest. All newly created
  * instances of class `Adventure` are identical to each other. To create other kinds of adventure
  * games, you will need to modify or replace the source code of this class. */
class Adventure {

  /** The title of the adventure game. */
  val title = "Viuluviikarin konserttisählinki musaopistolla"

  private val aula           = new Area("Aula", "Olet saapunut musiikkiopiston aulaan valmiina esiintymään oppilaskonsertissa.\nKonserttisalista kaikuu jo muiden esiintyjien harjoitukset.")
  private val kanslia        = new Area("Kanslia", "Olet epähuomiossa eksynyt kansliaan. Tuttu täti hymyilee työpisteeltään.\nHän ei ole nähnyt kadonneita nuotteja.")
  private val sointukäytävä  = new Area("Sointukäytävä", "Olet käytävällä. Siellä on pimeää.\nVahtimestari ei ole muistanut vaihtaa palanutta lamppua")
  private val bassokäytävä   = new Area("Bassokäytävä", "Olet käytävällä, josta pääsee backstagelle. Nyt on jo kiire! Esiintymisvuorosi on pian.")
  private val säestysluokka  = new Area("Säestysluokka", "Olet saapunut säestysluokkaan. Pianon päällä on iso pino nuotteja. Olisiko joku niistä sinun?")
  private val siivous        = new Area("Siivouskomero", "Onpas siivouskomerossa sotkuista! Voiko nuotti olla joutunut tänne?")
  private val backstage      = new Area("Backstage", "Backstage, viimeinen askel ennen esiintymistä.\nKonserttivaatteet päälle!")
  private val konserttisali  = new Area("Konserttisali", "Olet konserttisalissa. Yleisö istuu hiirenhiljaa hämärässä katsomossa ja lavalla lamppujen alla on lämmin.")
  private val destination    = konserttisali

           aula.setNeighbors(Vector("eteenpäin" -> sointukäytävä, "oikealle" -> kanslia,       "taaksepäin" -> aula,          "vasemmalle" -> bassokäytävä ))
        kanslia.setNeighbors(Vector(                                                           "taaksepäin" -> aula,          "vasemmalle" -> sointukäytävä))
  sointukäytävä.setNeighbors(Vector("eteenpäin" -> säestysluokka, "oikealle" -> kanslia,       "taaksepäin" -> aula,          "vasemmalle" -> konserttisali))
   bassokäytävä.setNeighbors(Vector(                              "oikealle" -> säestysluokka, "taaksepäin" -> aula,          "vasemmalle" -> backstage    ))
  säestysluokka.setNeighbors(Vector(                              "oikealle" -> sointukäytävä, "taaksepäin" -> bassokäytävä,  "vasemmalle" -> siivous      ))
        siivous.setNeighbors(Vector(                              "oikealle" -> säestysluokka,                                "vasemmalle" -> siivous      ))
      backstage.setNeighbors(Vector("eteenpäin" -> bassokäytävä,                                                              "vasemmalle" -> konserttisali))
  konserttisali.setNeighbors(Vector(                              "oikealle" -> sointukäytävä,                                "vasemmalle" -> backstage    ))

        this.siivous.addItem(new Item("debussy-nuotit", "Nämä ovat oikeat nuotit! Vielä pitäisi pukea siistit vaatteet ennen esitystä."))
        this.siivous.addItem(new Item("prokofjev-nuotit", "...mutta tänään et soita Prokofjevia."))
      this.backstage.addItem(new Item("konserttivaatteet", "Näissä kuteissa voisi nousta isommallekin lavalle!"))
      this.backstage.addItem(new Item("suklaakonvehti", "Tästä saa energiaa konserttiin."))
      this.backstage.addItem(new Item("nallekarkki", "Tästä saa energiaa konsertiin."))
  this.säestysluokka.addItem(new Item("kapustin-nuotit", "...mutta tänään et soita Kapustinia."))
  this.säestysluokka.addItem(new Item("bach-nuotit", "...mutta tänään et soita Bachia."))
  this.säestysluokka.addItem(new Item("nallekarkki", "Tästä saa energiaa konsertiin."))
  this.sointukäytävä.addItem(new Item("mozart-nuotit", "...mutta tänään et soita Mozartia."))
  this.sointukäytävä.addItem(new Item("rahkapulla", "Tästä saa energiaa konserttiin."))
  this.sointukäytävä.addItem(new Item("suklaakonvehti", "Tästä saa energiaa konserttiin."))
   this.bassokäytävä.addItem(new Item("huilu", "Voi ei! Joku on unohtanut huilunsa ikkunalaudalle!"))
   this.bassokäytävä.addItem(new Item("suklaakonvehti", "Tästä saa energiaa konserttiin."))
   this.bassokäytävä.addItem(new Item("rahkapulla", "Tästä saa energiaa konserttiin."))
        this.siivous.addItem(new Item("suklaakonvehti", "Tästä saa energiaa konserttiin."))
        this.siivous.addItem(new Item("rahkapulla", "Tästä saa energiaa konserttiin."))




  /** The character that the player controls in the game. */
  val player = new Player(aula)
  val saestaja = this.player.saestaja

  /** The number of turns that have passed since the start of the game. */
  var turnCount = 0
  /** The maximum number of turns that this adventure game allows before time runs out. */
  var timeLimit = this.player.remainingTurns


  /** Determines if the adventure is complete, that is, if the player has won. */
  def isComplete = this.player.location == this.destination && this.player.has("debussy-nuotit") && this.player.has("konserttivaatteet") && this.player.has("ruusu")



  /** Determines whether the player has won, lost, or quit, thereby ending the game. */
  def isOver = this.isComplete || this.player.hasQuit || this.player.remainingTurns == 0

  /** Returns a message that is to be displayed to the player at the beginning of the game. */
  def welcomeMessage = {
    "Olet saapunut musiikkiopistolle oppilaskonserttiin esiintymään. Säestysnuottisi on kuitenkin kadonnut johonkin opiston tiloihin ja vaatteetkin on vaihtamatta." +
    "\nEtsi nuotit ja vaihda vaatteet. Sen jälkeen olet valmis astumaan konserttisaliin, esittämään kappaleesi ja vastaanottamaan ruusun." +
    "\nSäestäjä auttaa, jos et muista kuka esityskappaleesi on säveltänyt. Mutta mistähän hänet mahtaisi löytää" +
    "\nEthän anna verensokerisi laskea liikaa, ettei voimat lopu kesken koitoksen." +
    "\nJos tarvitset vinkkejä, saat niitä komennolla 'apua'."
  }



  /** Returns a message that is to be displayed to the player at the end of the game. The message
    * will be different depending on whether or not the player has completed their quest. */
  def goodbyeMessage = {
    if (this.isComplete) {
      "Ehdit saliin juuri ajoissa! Upeasti soitettu!"
    } else if (this.player.hasQuit) {
      "Meinasit luistaa konsertista? Pöh!"
    } else {
      "Voi kääk! Esiintymisvuorosi oli jo.\nGame over!"
    }
  }


  /** Plays a turn by executing the given in-game command, such as "go west". Returns a textual
    * report of what happened, or an error message if the command was unknown. In the latter
    * case, no turns elapse. */
  def playTurn(command: String) = {
    this.player.accompanistLocation()
    val action = new Action(command)
    val outcomeReport = action.execute(this.player)
    if (outcomeReport.isDefined && command != "apua") {
      this.turnCount += 1
      this.player.currentTurns()
    }
    outcomeReport.getOrElse("Unknown command: \"" + command + "\".")
  }


}

