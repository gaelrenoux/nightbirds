package fr.renoux.nightbirds.game

import org.junit.Assert
import org.junit.Before
import org.junit.Test
import fr.renoux.nightbirds.rules.generics.Board
import fr.renoux.nightbirds.rules.generics.Family
import fr.renoux.nightbirds.rules.generics.Cash
import fr.renoux.nightbirds.rules.generics.Card
import fr.renoux.nightbirds.playercontract.Blue

class GenericCardTest {
  
  var player : StubPlayer = new StubPlayer(new Board)
  var card : Card = null
  var family :Family = null
  var otherCard : Card = null
  
  
  @Before
  def prepare = {
    family = Family(Blue)
    val b = new Board(family)
    card = new StubCardWithTarget(player, b, family)
  }

  @Test
  def testFamilyPaying = {
    family.store(Cash(5))
    card.store(Cash(1))
    
    Assert.assertEquals(Cash(5), family.cash)   
    Assert.assertEquals(Cash(1), card.cash)    
    
    val taken = card.take(Cash(2))
    
    Assert.assertEquals(Cash(2), taken)
    Assert.assertEquals(Cash(4), family.cash) 
    Assert.assertEquals(Cash(0), card.cash)
  }

  @Test
  def testFamilyBroke = {    
    family.store(Cash(5))
    card.store(Cash(1))
    
    Assert.assertEquals(Cash(5), family.cash)   
    Assert.assertEquals(Cash(1), card.cash)      
    
    val taken = card.take(Cash(8))
    Assert.assertEquals(Cash(6), taken)
    Assert.assertEquals(Cash(0), family.cash) 
    Assert.assertEquals(Cash(0), card.cash)
  }
  
  def testHit = {
    card.store(Cash(22))
    card.hit()
    Assert.assertFalse(card.available)
    Assert.assertEquals(Cash(0), card.cash)
    card.sleep
    Assert.assertFalse(card.available)
    card.sleep
    Assert.assertTrue(card.available)
    
  }
}