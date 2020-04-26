import static org.junit.Assert.*;

import org.junit.Test;

public class TestDummyDesign {
    
	DummyDesign testObject = new DummyDesign();

	@Test
	public void testIntegrante1() {
		assertEquals(1, this.testObject.integrante1());
	}

}
