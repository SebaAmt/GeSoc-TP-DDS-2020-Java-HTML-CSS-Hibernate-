import static org.junit.Assert.*;

import org.junit.Test;

public class TestDummyDesign {
    
	DummyDesign testObject = new DummyDesign();

	@Test
	public void testIntegrante1() {
		assertEquals(1, this.testObject.integrante1());
	}

	@Test
	public void testIntegrante2(){
		assertEquals(2, this.testObject.integrante2());
	}

	@Test
	public void testIntegrante4() {
		assertEquals(4, testObject.integrante4());
	}
}

