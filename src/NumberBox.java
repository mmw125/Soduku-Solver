import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Point;
import java.util.ArrayList;

import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

public class NumberBox
{
	private static NumberBox[][] boxes;
	
	public static boolean isSolved()
	{
		for(int x = 0; x < 9; x++)
		{
			for(int y = 0; y < 9; y++)
			{
				if(boxes[x][y].getValue() == 0)
				{
					return false;
				}
			}
		}
		return true;
	}
	
	public static NumberBox boxWithLeastOptions()
	{
		NumberBox boxWithLeastOptions = null;
		int leastOptions = 0;
		for(int x = 0; x < 9; x++)
		{
			for(int y = 0; y < 9; y++)
			{
				if(boxes[x][y].getValue() == 0)
				{
					if(boxWithLeastOptions == null)
					{
						boxWithLeastOptions = boxes[x][y];
						leastOptions = boxWithLeastOptions.options();
					}
					else
					{
						int options = boxes[x][y].options();
						if(options == 0)
						{
							return null;
						}
						if(options < leastOptions)
						{
							boxWithLeastOptions = boxes[x][y];
						}
					}
				}
			}
		}
		return boxWithLeastOptions;
	}

	public static void setUpPuzzle1()
	{
		boxes[0][0].setValue(1);
		boxes[1][1].setValue(2);
		boxes[2][1].setValue(3);
		boxes[3][1].setValue(1);
		boxes[5][0].setValue(1);
		boxes[5][1].setValue(6);
		boxes[8][0].setValue(4);
		boxes[7][1].setValue(5);
		boxes[7][2].setValue(6);
		boxes[0][3].setValue(9);
		boxes[1][5].setValue(5);
		boxes[2][3].setValue(4);
		boxes[3][4].setValue(5);
		boxes[4][4].setValue(6);
		boxes[5][4].setValue(7);
		boxes[6][5].setValue(8);
		boxes[7][3].setValue(7);
		boxes[8][5].setValue(3);
		boxes[1][6].setValue(4);
		boxes[1][7].setValue(3);
		boxes[0][8].setValue(2);
		boxes[3][7].setValue(7);
		boxes[3][8].setValue(8);
		boxes[5][7].setValue(1);
		boxes[6][7].setValue(9);
		boxes[7][7].setValue(8);
		boxes[8][8].setValue(7);
	}
	
	public static void fillPanelWithBoxes(Container con)
	{
		getBoxes();
		for(int x = 0; x < 9; x++)
		{
			for(int y = 0; y < 9; y++)
			{
				con.add(boxes[x][y].getField());
			}
		}
	}
	
	public static NumberBox[][] getBoxes()
	{
		if(boxes == null)
		{
			boxes = new NumberBox[9][9];
			for(int x = 0; x < 9; x++)
			{
				for(int y = 0; y < 9; y++)
				{
					boxes[x][y] = new NumberBox(new Point(x, y));
					if((x/3 + y/3) % 2 == 0){
						boxes[x][y].getField().setBackground(Color.DARK_GRAY);
					}
				}
			}
		}
		return boxes;
	}
	
	private Point position;
	private JSpinner spinner;
	
	private NumberBox(Point position)
	{
		this.position = position;
		spinner = new JSpinner();
		spinner.setModel(new SpinnerNumberModel(0, 0, 9, 1));
	}
	
	public int getValue()
	{
		return (int) spinner.getValue();
	}
	
	public Component getField()
	{
		return spinner;
	}
	
	private int options() {
		int options = 9;
		for(int i = 1; i <= 9; i++)
		{
			for(NumberBox box : relatedTo())
			{
				if(box.getValue() == i)
				{
					options--;
					break;
				}
			}
		}
		if(options == 0){
			System.out.println(this + " Returns 0 for options");
		}
		return options;
	}
	
	private ArrayList<NumberBox> relatedTo()
	{
		ArrayList<NumberBox> relatedTo = new ArrayList<NumberBox>();
		for(int x = 0; x < 9; x++)
		{
			for(int y = 0; y < 9; y++)
			{
				if(position.x == x || position.y == y || (position.x / 3 == x / 3 && position.y / 3 == y / 3))
				{
					relatedTo.add(boxes[x][y]);
				}
			}
		}
		return relatedTo;
	}
	
	@Override
	public String toString()
	{
		return position.x + ", " + position.y + ": " + getValue();
	}
	
	public void setValue(int value)
	{
		spinner.setValue(value);
		spinner.revalidate();
	}

	public ArrayList<Integer> getOptions() {
		ArrayList<Integer> list = new ArrayList<Integer>();
		for(int i = 1; i <= 9; i++)
		{
			list.add(i);
			for(NumberBox box : relatedTo())
			{
				if(box.getValue() == i)
				{
					list.remove(list.size() - 1);
					break;
				}
			}
		}
		if(list.size() == 0){
			System.out.println(this + " Returns 0 for options");
		}
		return list;
	}
}
