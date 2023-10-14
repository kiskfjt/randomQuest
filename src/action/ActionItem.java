package action;

import chr.Chr;

public class ActionItem extends Action {

	public ActionItem(Chr me) {
		super(me);
		name = "どうぐ";
	}
	
	/**
	 * 
	 */
	@Override
	public void playerTarget() {
		me.item.playerTarget();
	}

	@Override
	public void execute() {
		me.item.execute();
	}
}
