package info.ajaxplorer.synchro.gui;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Date;

import info.ajaxplorer.client.model.Node;
import info.ajaxplorer.synchro.Manager;
import info.ajaxplorer.synchro.model.SyncChange;
import info.ajaxplorer.synchro.model.SyncChangeValue;
import info.ajaxplorer.synchro.model.SyncLog;

import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;


public class LogViewer extends org.eclipse.swt.widgets.Composite {
	private SashForm sashForm1;
	private Table table1;
	private TableColumn table1ColumnDate;
	private TableColumn table1ColumnResult;
	private Label label1;
	private TableColumn table1ColumnSummary;
	private Composite composite1;
	private Table table2;
	private Label label2;
	private Composite composite2;
	private TableColumn table2ColumnStatus;
	private TableColumn table2ColumnFile;
	
	Menu solveMenu;
	MenuItem itemSolveKeepMine;
	MenuItem itemSolveKeepTheir;
	MenuItem itemSolveKeepBoth;
	TableItem currentTarget;

	/**
	* Overriding checkSubclass allows this class to extend org.eclipse.swt.widgets.Composite
	*/	
	protected void checkSubclass() {
	}
	
	public LogViewer(org.eclipse.swt.widgets.Composite parent, int style) {
		super(parent, style);
		//initGUI();
	}

	public void clearSynchroLog(){
		table1.removeAll();
		table2.removeAll();
	}
	
	public void loadSynchroLog(Node synchroNode){
		try {
			table1.removeAll();
			Collection<SyncLog> logs = Manager.getInstance().getSyncLogDao().queryForEq("synchroNode_id", synchroNode.id);
			for(SyncLog log:logs){
				TableItem it = new TableItem(table1, SWT.NONE);
				it.setText(0, new Date(log.jobDate).toString());
				it.setText(1, log.jobStatus);
				it.setText(2, log.jobSummary);
				it.setData(log);
			}
			for (int i = 0; i < 2; i++) {
		        TableColumn column = table1.getColumn(i);
		        column.pack();
		    }
			table1.getColumn(2).setWidth(table1.getBounds().width - table1.getColumn(0).getWidth() - table1.getColumn(1).getWidth()-20);
			
			table2.removeAll();
			Collection<SyncChange> changes = Manager.getInstance().getSyncChangeDao().queryForEq("jobId", synchroNode.id);
			for(SyncChange change:changes){
				TableItem it = new TableItem(table2, SWT.NONE);
				SyncChangeValue v = change.getChangeValue();
				it.setText(0, change.getKey());
				it.setText(1, "Status : "+v.getStatusString());
				it.setText(2, "Task : "+v.getTaskString());
				it.setData(change);
			}
			
			for (int i = 0; i < 2; i++) {
		        TableColumn column = table2.getColumn(i);
		        column.pack();
		    }
			table2.getColumn(2).setWidth(table2.getBounds().width - table2.getColumn(0).getWidth() - table2.getColumn(1).getWidth()-20);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void initGUI() {
		try {
			FormLayout thisLayout = new FormLayout();
			this.setLayout(thisLayout);
			this.setSize(581, 310);

			{
				FormData sashForm1LData = new FormData();
				sashForm1LData.left =  new FormAttachment(0, 1000, 5);
				sashForm1LData.top =  new FormAttachment(0, 1000, 5);
				sashForm1LData.width = 450;
				sashForm1LData.height = 300;
				sashForm1LData.right =  new FormAttachment(1000, 1000, -5);
				sashForm1LData.bottom =  new FormAttachment(1000, 1000, -5);
				sashForm1 = new SashForm(this, SWT.VERTICAL);
				sashForm1.SASH_WIDTH = 5;
				sashForm1.setLayoutData(sashForm1LData);
				{
					composite1 = new Composite(sashForm1, SWT.NONE);
					FormLayout composite1Layout = new FormLayout();
					composite1.setLayout(composite1Layout);
					{
						label1 = new Label(composite1, SWT.NONE);
						label1.setText("Synchronisations Log");
						FormData label1FormData = new FormData();
						label1FormData.left =  new FormAttachment(0, 1000, 5);
						label1FormData.top =  new FormAttachment(0, 1000, 10);
						label1FormData.right = new FormAttachment(1000, 1000, 0);
						label1FormData.height = 20;
						label1.setLayoutData(label1FormData);
					}					
					{
						table1 = new Table(composite1, SWT.MULTI | SWT.BORDER | SWT.FULL_SELECTION);
						table1.setLinesVisible(true);
						table1.setHeaderVisible(true);
						FormData table1FormData = new FormData();
						table1FormData.left =  new FormAttachment(0, 1000, 0);
						table1FormData.top =  new FormAttachment(0, 1000, 30);
						table1FormData.right = new FormAttachment(1000, 1000, 0);
						table1FormData.bottom = new FormAttachment(1000, 1000, 0);
						table1FormData.height = 10;
						table1.setLayoutData(table1FormData);
						{
							table1ColumnDate = new TableColumn(table1, SWT.NONE);
							table1ColumnDate.setText("Date");
							//table1ColumnDate.setWidth(120);
						}
						{
							table1ColumnResult = new TableColumn(table1, SWT.NONE);
							table1ColumnResult.setText("Result");
							//table1ColumnResult.setWidth(129);
						}
						{
							table1ColumnSummary = new TableColumn(table1, SWT.NONE);
							table1ColumnSummary.setText("Summary");
							//table1ColumnSummary.setWidth(317);
						}
					}
				}
				{
					composite2 = new Composite(sashForm1, SWT.NONE);
					FormLayout composite1Layout = new FormLayout();
					composite2.setLayout(composite1Layout);
					{
						label2 = new Label(composite2, SWT.NONE);
						label2.setText("Interrupted tasks or unresolved conflicts (right-click on each if an action is necessary)");
						FormData label1FormData = new FormData();
						label1FormData.left =  new FormAttachment(0, 1000, 5);
						label1FormData.top =  new FormAttachment(0, 1000, 10);
						label1FormData.right = new FormAttachment(1000, 1000, 0);
						label1FormData.height = 20;
						label2.setLayoutData(label1FormData);
					}					
					{
						table2 = new Table(composite2, SWT.MULTI | SWT.BORDER | SWT.FULL_SELECTION);
						table2.setLinesVisible(true);
						table2.setHeaderVisible(true);
						FormData table1FormData = new FormData();
						table1FormData.left =  new FormAttachment(0, 1000, 0);
						table1FormData.top =  new FormAttachment(0, 1000, 30);
						table1FormData.right = new FormAttachment(1000, 1000, 0);
						table1FormData.bottom = new FormAttachment(1000, 1000, 0);
						table1FormData.height = 10;
						table2.setLayoutData(table1FormData);
						{
							table2ColumnFile = new TableColumn(table2, SWT.NONE);
							table2ColumnFile.setText("File/Folder name");
						}
						{
							table2ColumnStatus = new TableColumn(table2, SWT.NONE);
							table2ColumnStatus.setText("Status");
						}
						{
							TableColumn table2ColumnTask = new TableColumn(table2, SWT.NONE);
							table2ColumnTask.setText("Task");
						}
					}
				}			
			}
			table2.addMouseListener(new MouseListener() {
				
				@Override
				public void mouseUp(MouseEvent arg0) {					
				}
				
				@Override
				public void mouseDown(MouseEvent arg0) {
					if(arg0.button != 3) return;
					Point p = new Point(arg0.x, arg0.y);
					Point p2 = new Point(arg0.x + getShell().getBounds().x, arg0.y + getShell().getBounds().y);					
					currentTarget = table2.getItem(p);	
					p2 = table2.toDisplay(p);
					if(currentTarget != null){
						solveMenu.setLocation(p2);
						solveMenu.setVisible(true);
					}
				}
				
				@Override
				public void mouseDoubleClick(MouseEvent arg0) {
				}
			});
			 solveMenu = new Menu(this.getShell(),
                     SWT.POP_UP);
			 itemSolveKeepMine = new MenuItem(solveMenu, SWT.PUSH);
			 itemSolveKeepMine.setText("Keep my version");
			 itemSolveKeepMine.setData("mine");
			 SelectionListener listener = new SelectionListener() {				 
				 public void widgetSelected(SelectionEvent arg0) {
					 if(currentTarget == null) return;
					 String k = ((SyncChange)currentTarget.getData()).getKey();
					 System.out.println("Should solve " + k + " with command " + arg0.widget.getData());
					 currentTarget = null;
					 solveMenu.setVisible(false);
				 }				 
				 public void widgetDefaultSelected(SelectionEvent arg0) {}
			 };			 
			 itemSolveKeepMine.addSelectionListener(listener);
			 itemSolveKeepTheir = new MenuItem(solveMenu, SWT.PUSH);
			 itemSolveKeepTheir.setText("Keep remote version");
			 itemSolveKeepTheir.setData("their");
			 itemSolveKeepTheir.addSelectionListener(listener);
             itemSolveKeepBoth = new MenuItem(solveMenu, SWT.PUSH);
             itemSolveKeepBoth.setText("Keep both versions");
             itemSolveKeepBoth.setData("both");
             itemSolveKeepMine.addSelectionListener(listener);
			this.layout();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}