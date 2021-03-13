package o1.adventure


/** The class `Action` represents actions that a player may take in a text adventure game.
  * `Action` objects are constructed on the basis of textual commands and are, in effect,
  * parsers for such commands. An action object is immutable after creation.
  * @param input  a textual in-game command such as "go east" or "rest" */
class Action(input: String) {

  private val commandText = input.trim.toLowerCase
  private val verb        = commandText.takeWhile( _ != ' ' )
  private val modifiers   = commandText.drop(verb.length).trim


  /** Causes the given player to take the action represented by this object, assuming
    * that the command was understood. Returns a description of what happened as a result
    * of the action (such as "You go west."). The description is returned in an `Option`
    * wrapper; if the command was not recognized, `None` is returned. */
  def execute(actor: Player) = this.verb match {
    case "mene"        => Some(actor.mene(this.modifiers))
    case "huilaa"      => Some(actor.huilaa())
    case "lopeta"      => Some(actor.lopeta())
    case "inventaario" => Some(actor.inventaario)
    case "ota"         => Some(actor.ota(modifiers))
    case "tutki"       => Some(actor.tutki(modifiers))
    case "pudota"      => Some(actor.pudota(modifiers))
    case "esiinny"     => Some(actor.esiinny())
    case "haukkaa"     => Some(actor.haukkaa(modifiers))
    case "kysy"        => Some(actor.kysy())
    case "paniikki"    => Some(actor.paniikki())
    case "apua"        => Some(this.help())
    case "vuorot"      => Some(actor.vuorot)
    case other         => None
  }

  def help() = {
    "Voittaaksesi pelin sinun tulee saada haltuusi oikeat nuotit, vaihtaa esiintymisvaatteet ja esittää kappaleesi konserttisalissa pidettävässä oppilaskonsertissa, jotta saat ruusun." +
    "\nMusiikkiopistolta löytyy eri säveltäjien nuotteja. Selvittääksesi mikä nuoteista on oikea, voit tutkia löytämääsi nuottia komennolla 'tutki'" +
    "\ntai kysyä neuvoa säestäjältä komennolla 'kysy', jos säestäjä on kanssasi samassa tilassa." +
    "\nPääset liikkumaan tilasta toiseen komennolla 'mene' sekä valitsemalla yhden annetuista suunnista." +
    "\nMuita komentoja ovat:\n'huilaa', jolloin lepäät hetken\n'lopeta', joka lopettaa pelin\n'inventaario', joka tulostaa listan hallussasi olevista tavaroista" +
    "\n'ota', jolla voit poimia esineen itsellesi\n'pudota', joka jättää hallussasi olevan esineen tilaan jossa olet\n'esiinny', jota tarvitset pelin lopussa " +
    "saadaksesi pelin voittamiseen tarvittavan ruusun\n'haukkaa', jolla voit syödä tilassa olevan herkun\n'paniikki', jolla voit selvittää säestäjäsi olinpaikan opistolla." +
    " Muista kuitenkin, että tällä komennolla menetät kaksi ylimääräistä toimintavuoroa." +
    "\n'vuorot', jolla voit tarkastaa jäljellä olevat vuorosi" +
    "\nJotta vuorot eivät lopu kesken, pitää sinun muistaa syödä musiikkiopiston tiloista löytyviä herkkuja. Jokainen herkku antaa sinulle viisi toimintavuoroa lisää." +
    "\nTsemppiä koitokseen!"
  }


  /** Returns a textual description of the action object, for debugging purposes. */
  override def toString = this.verb + " (modifiers: " + this.modifiers + ")"


}

