import com.googlecode.junittoolbox.SuiteClasses;
import com.googlecode.junittoolbox.WildcardPatternSuite;
import org.junit.runner.RunWith;

@RunWith(WildcardPatternSuite.class)
@SuiteClasses({"**/*Test.class"})
public class AllTestsSuite {
    // If it's a *Test, it will be included.
}