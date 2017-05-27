package whiteBoxTests;

import data.Result;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestResult {

	@Test
	public void testConstructorAndGetter() {
		List<List<String>> testdata = new ArrayList<List<String>>();
		List<List<String>> result = new ArrayList<List<String>>();
		List<String> simple = new ArrayList<String>();
		simple.add("1");
		simple.add("2");
		testdata.add(simple);
		simple.remove(0);
		simple.add("3");
		simple.add("test");
		testdata.add(simple);
		
		Result r = new Result(testdata);
		result = r.getDiagrammData();
		assertEquals(testdata, result);
	}

}
