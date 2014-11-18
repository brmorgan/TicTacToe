// Brandon Morgan
// COSC4740
// 6 October 2014
// Program 3, Tic Tac Toe

/*	Compiled on API19 (Android 4.4.2 Kit Kat)
 * 	Minimum recommended: API16 (Android 4.1 Jelly Bean)
 * 	Tested on Nexus 5 emulator, Nexus One emulator, Samsung Galaxy S4
 * 	Should function regardless of device screen size
 * 
 * 	X Plays first, tap the screen to place your move.
 * 	Play alternates between X and O until victory (three in a row 
 * 	or draw (the board is filled with no winner)
 * 	Tap the dialog to start again.
 * 	Tapping on a spot that already has an X or O yields no response, tap again in an appropriate spot.
 * 	Tapping outside the board yields no response. Tap again inside the board.
*/
package edu.morgan.tictactoe;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends Activity {
	
	Paint black;
	Paint blue;
	Paint red;
	Canvas canvas;
	ImageView board;
	Bitmap boardbmp;
	boolean xturn;
	boolean xhas[], ohas[];
	int taken;
	int boardsize=0;
	public final Context myContext = this;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Display display = getWindowManager().getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		int width = size.x;
		int height = size.y;
		if(width < height)
			boardsize = width;
		else
			boardsize = height;
		
		board = (ImageView)findViewById(R.id.board);
		boardbmp = Bitmap.createBitmap(boardsize, boardsize, Bitmap.Config.ARGB_8888);
		canvas = new Canvas(boardbmp);
		board.setImageBitmap(boardbmp);
		board.setOnTouchListener(new myTouchListener());
		
		black = new Paint();
		black.setColor(Color.BLACK);
		black.setStyle(Paint.Style.STROKE);
		blue = new Paint();
		blue.setColor(Color.BLUE);
		blue.setStyle(Paint.Style.STROKE);
		red = new Paint();
		red.setColor(Color.RED);
		red.setStyle(Paint.Style.STROKE);
		
		canvas.drawColor(Color.WHITE);
		canvas.drawLine(boardsize/3, 0, boardsize/3, boardsize, black);
		canvas.drawLine(2*boardsize/3, 0, 2*boardsize/3, boardsize, black);
		canvas.drawLine(0, boardsize/3, boardsize, boardsize/3, black);
		canvas.drawLine(0, 2*boardsize/3, boardsize, 2*boardsize/3, black);
		
		xhas = new boolean[9];
		ohas = new boolean[9];
		for(int i=0; i<9; i++)
		{
			xhas[i]=false;
			ohas[i]=false;
		}
		xturn = true;
		taken = 0;
	}
	
	public void drawIt()
	{
		board.setImageBitmap(boardbmp);
		board.invalidate();
	}
	
	public void resetBoard()
	{
		canvas.drawColor(Color.WHITE);
		canvas.drawLine(boardsize/3, 0, boardsize/3, boardsize, black);
		canvas.drawLine(2*boardsize/3, 0, 2*boardsize/3, boardsize, black);
		canvas.drawLine(0, boardsize/3, boardsize, boardsize/3, black);
		canvas.drawLine(0, 2*boardsize/3, boardsize, 2*boardsize/3, black);
		
		xhas = new boolean[9];
		ohas = new boolean[9];
		for(int i=0; i<9; i++)
		{
			xhas[i]=false;
			ohas[i]=false;
		}
		xturn = true;
		taken = 0;
		
		drawIt();
	}
	
	public void xVictory()
	{
		Dialog dialog = null;
		AlertDialog.Builder popup;
		popup = new AlertDialog.Builder(myContext);
		popup.setMessage("X Wins!")
			.setCancelable(false)
			.setPositiveButton("Reset Board", new DialogInterface.OnClickListener(){
				public void onClick(DialogInterface dialog, int id){resetBoard();}
			});
		dialog = popup.create();
		
		if(xhas[0])
		{
			if(xhas[1] && xhas[2])
			{
				dialog.show();
				return;
			}
			else if(xhas[3] && xhas[6])
			{
				dialog.show();
				return;
			}
			else if(xhas[4] && xhas[8])
			{
				dialog.show();
				return;
			}
		}
		if(xhas[2])
		{
			if(xhas[5] && xhas[8])
			{
				dialog.show();
				return;
			}
			else if(xhas[4] && xhas[6])
			{
				dialog.show();
				return;
			}
		}
		if(xhas[1] && xhas[4] && xhas[7])
		{
			dialog.show();
			return;
		}
		if(xhas[3] && xhas[4] && xhas[5])
		{
			dialog.show();
			return;
		}
		if(xhas[6] && xhas[7] && xhas[8])
		{
			dialog.show();
			return;
		}
		if(taken == 9)
		{
			popup.setMessage("Draw")
			.setCancelable(false)
			.setPositiveButton("Reset Board", new DialogInterface.OnClickListener(){
				public void onClick(DialogInterface dialog, int id){resetBoard();}
			});
			dialog = popup.create();
			dialog.show();
			return;
		}
	}
	
	public void oVictory()
	{
		Dialog dialog = null;
		AlertDialog.Builder popup;
		popup = new AlertDialog.Builder(myContext);
		popup.setMessage("O Wins!")
			.setCancelable(false)
			.setPositiveButton("Reset Board", new DialogInterface.OnClickListener(){
				public void onClick(DialogInterface dialog, int id){resetBoard();}
			});
		dialog = popup.create();

		if(ohas[0])
		{
			if(ohas[1] && ohas[2])
			{
				dialog.show();
				return;
			}
			else if(ohas[3] && ohas[6])
			{
				dialog.show();
				return;
			}
			else if(ohas[4] && ohas[8])
			{
				dialog.show();
				return;
			}
		}
		if(ohas[2])
		{
			if(ohas[5] && ohas[8])
			{
				dialog.show();
				return;
			}
			else if(ohas[4] && ohas[6])
			{
				dialog.show();
				return;
			}
		}
		if(ohas[1] && ohas[4] && ohas[7])
		{
			dialog.show();
			return;
		}
		if(ohas[3] && ohas[4] && ohas[5])
		{
			dialog.show();
			return;
		}
		if(ohas[6] && ohas[7] && ohas[8])
		{
			dialog.show();
			return;
		}
		if(taken == 9)
		{
			popup.setMessage("Draw")
			.setCancelable(false)
			.setPositiveButton("Reset Board", new DialogInterface.OnClickListener(){
				public void onClick(DialogInterface dialog, int id){resetBoard();}
			});
			dialog = popup.create();
			dialog.show();
			return;
		}
	}
	
	class myTouchListener implements View.OnTouchListener
	{
		@Override
		public boolean onTouch(View v, MotionEvent event)
		{
			int x = (int) event.getX();
			int y = (int) event.getY();
			
			if(x<boardsize/3) // touch is in the left column
			{
				if(y<boardsize/3) // touch is in the top row
				{
					if(!xhas[0] && !ohas[0])
					{
						if(xturn)
						{
							xhas[0] = true;
							taken++;
							canvas.drawLine(0, 0, boardsize/3, boardsize/3, red);
							canvas.drawLine(0, boardsize/3, boardsize/3, 0, red);
							xturn = false;
							xVictory();
						}
						else
						{
							ohas[0] = true;
							taken++;
							canvas.drawCircle(boardsize/6, boardsize/6, boardsize/9, blue);
							xturn = true;
							oVictory();
						}
					}
				}
				else if(y<2*boardsize/3) // touch is in the middle row
				{
					if(!xhas[3] && !ohas[3])
					{
						if(xturn)
						{
							xhas[3] = true;
							taken++;
							canvas.drawLine(0, boardsize/3, boardsize/3, 2*boardsize/3, red);
							canvas.drawLine(0, 2*boardsize/3, boardsize/3, boardsize/3, red);
							xturn = false;
							xVictory();
						}
						else
						{
							ohas[3] = true;
							taken++;
							canvas.drawCircle(boardsize/6, boardsize/2, boardsize/9, blue);
							xturn = true;
							oVictory();
						}
					}
				}
				else if(y<boardsize) // touch is in the bottom row
				{
					if(!xhas[6] && !ohas[6])
					{
						if(xturn)
						{
							xhas[6] = true;
							taken++;
							canvas.drawLine(0, 2*boardsize/3, boardsize/3, boardsize, red);
							canvas.drawLine(0, boardsize, boardsize/3, 2*boardsize/3, red);
							xturn = false;
							xVictory();
						}
						else
						{
							ohas[6] = true;
							taken++;
							canvas.drawCircle(boardsize/6, 5*boardsize/6, boardsize/9, blue);
							xturn = true;
							oVictory();
						}
					}
				}
			}
			else if (x<2*boardsize/3) // touch is in the middle column
			{
				if(y<boardsize/3) // touch is in the top row
				{
					if(!xhas[1] && !ohas[1])
					{
						if(xturn)
						{
							xhas[1] = true;
							taken++;
							canvas.drawLine(boardsize/3, 0, 2*boardsize/3, boardsize/3, red);
							canvas.drawLine(boardsize/3, boardsize/3, 2*boardsize/3, 0, red);
							xturn = false;
							xVictory();
						}
						else
						{
							ohas[1] = true;
							taken++;
							canvas.drawCircle(boardsize/2, boardsize/6, boardsize/9, blue);
							xturn = true;
							oVictory();
						}
					}
				}
				else if(y<2*boardsize/3) // touch is in the middle row
				{
					if(!xhas[4] && !ohas[4])
					{
						if(xturn)
						{
							xhas[4] = true;
							taken++;
							canvas.drawLine(boardsize/3, boardsize/3, 2*boardsize/3, 2*boardsize/3, red);
							canvas.drawLine(boardsize/3, 2*boardsize/3, 2*boardsize/3, boardsize/3, red);
							xturn = false;
							xVictory();
						}
						else
						{
							ohas[4] = true;
							taken++;
							canvas.drawCircle(boardsize/2, boardsize/2, boardsize/9, blue);
							xturn = true;
							oVictory();
						}
					}
				}
				else if(y<boardsize) // touch is in the bottom row
				{
					if(!xhas[7] && !ohas[7])
					{
						if(xturn)
						{
							xhas[7] = true;
							taken++;
							canvas.drawLine(boardsize/3, 2*boardsize/3, 2*boardsize/3, boardsize, red);
							canvas.drawLine(boardsize/3, boardsize, 2*boardsize/3, 2*boardsize/3, red);
							xturn = false;
							xVictory();
						}
						else
						{
							ohas[7] = true;
							taken++;
							canvas.drawCircle(boardsize/2, 5*boardsize/6, boardsize/9, blue);
							xturn = true;
							oVictory();
						}
					}
				}
			}
			else if(x<boardsize) // touch is in the right column
			{
				if(y<boardsize/3) // touch is in the top row
				{
					if(!xhas[2] && !ohas[2])
					{
						if(xturn)
						{
							xhas[2] = true;
							taken++;
							canvas.drawLine(2*boardsize/3, 0, boardsize, boardsize/3, red);
							canvas.drawLine(2*boardsize/3, boardsize/3, boardsize, 0, red);
							xturn = false;
							xVictory();
						}
						else
						{
							ohas[2] = true;
							taken++;
							canvas.drawCircle(5*boardsize/6, boardsize/6, boardsize/9, blue);
							xturn = true;
							oVictory();
						}
					}
				}
				else if(y<2*boardsize/3) // touch is in the middle row
				{
					if(!xhas[5] && !ohas[5])
					{
						if(xturn)
						{
							xhas[5] = true;
							taken++;
							canvas.drawLine(2*boardsize/3, boardsize/3, boardsize, 2*boardsize/3, red);
							canvas.drawLine(2*boardsize/3, 2*boardsize/3, boardsize, boardsize/3, red);
							xturn = false;
							xVictory();
						}
						else
						{
							ohas[5] = true;
							taken++;
							canvas.drawCircle(5*boardsize/6, boardsize/2, boardsize/9, blue);
							xturn = true;
							oVictory();
						}
					}
				}
				else if(y<boardsize) // touch is in the bottom row
				{
					if(!xhas[8] && !ohas[8])
					{
						if(xturn)
						{
							xhas[8] = true;
							taken++;
							canvas.drawLine(2*boardsize/3, 2*boardsize/3, boardsize, boardsize, red);
							canvas.drawLine(2*boardsize/3, boardsize, boardsize, 2*boardsize/3, red);
							xturn = false;
							xVictory();
						}
						else
						{
							ohas[8] = true;
							taken++;
							canvas.drawCircle(5*boardsize/6, 5*boardsize/6, boardsize/9, blue);
							xturn = true;
							oVictory();
						}
					}
				}
			}
			
			drawIt();
			return true;
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
