import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;

import static junit.framework.TestCase.assertTrue;

public class SauceDemoTest {

    @Test
    public void testEmptyCollection() {
        Collection collection = new ArrayList();
        assertTrue(collection.isEmpty());
    }
}
