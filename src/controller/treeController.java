package controller;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Tree;
import org.zkoss.zul.Treecell;

public class treeController extends GenericForwardComposer<Component> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -70287581855844816L;

	Tree tree;
	
	Treecell treeCellHome;
	Treecell treeCellTambah;
	Treecell treeCellHistory;
	Treecell treeCellImport;
	Treecell treeCellHomeHasil;

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		// TODO Auto-generated method stub
		super.doAfterCompose(comp);
	}
	
	public void onClick$treeCellHome(){
		Executions.getCurrent().sendRedirect("dashboard.zul");
	}
	
	public void onClick$treeCellTambah(){
		Executions.getCurrent().sendRedirect("keloladatasiswa.zul");
	}
	
	public void onClick$treeCellHistory(){
		Executions.getCurrent().sendRedirect("historytryoutsiswa.zul");
	}
	
	public void onClick$treeCellImport(){
		Executions.getCurrent().sendRedirect("importfile.zul");
	}
	
	public void onClick$treeCellHomeHasil(){
		Executions.getCurrent().sendRedirect("hasiltryoutsiswa.zul");
	}
	

}
