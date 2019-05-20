package fabler.fablededitor;

import org.junit.Test;

import java.util.ArrayList;

import fabler.fablededitor.datatype.DraftDataItemModel;
import fabler.fablededitor.utilities.RenderingUtils;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
  @Test
  public void addition_isCorrect() {
    RenderingUtils renderingUtils = new RenderingUtils();
    ArrayList<DraftDataItemModel> types = new ArrayList<>();
    //types.add(new TextType());
    //types.add(new TextType());
    //types.add(new ImageType());
    //types.add(new HRType());
    renderingUtils.render(types);
  }

  class Group {
    private int start;
    private int end;

    public Group(int start, int end) {
      this.start = start;
      this.end = end;
    }

    public int getStart() {
      return start;
    }

    public void setStart(int start) {
      this.start = start;
    }

    public int getEnd() {
      return end;
    }

    public void setEnd(int end) {
      this.end = end;
    }

    @Override
    public String toString() {
      return "Group{" +
       "start=" + start +
       ", end=" + end +
       '}';
    }
  }
}