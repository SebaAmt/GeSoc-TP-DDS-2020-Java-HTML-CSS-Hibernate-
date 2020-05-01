import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TestDummyDesign {

	DummyDesign testObject = new DummyDesign();

	@Test
	public void testIntegrante1() {
		assertEquals(1, this.testObject.integrante1());
	}

	@Test
	public void testIntegrante2() {
		assertEquals(2, this.testObject.integrante2());
	}

	@Test
	public void testIntegrante3() {
		assertEquals(3, testObject.integrante3());
	}

	@Test
	public void testIntegrante4() {
		assertEquals(4, testObject.integrante4());
	}
	
	@Test
	public void testIntegrante5() {
		assertEquals(5, testObject.integrante5());
	}
	
	@Test
	public void testIntegrante6() {
		assertEquals(5, testObject.integrante6());
	}
}
