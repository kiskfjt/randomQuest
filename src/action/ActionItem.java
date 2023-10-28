package action;

import chr.Chr;

public class ActionItem extends Action {

	public ActionItem(Chr me) {
		super(me);
		name = "どうぐ";
		rangeMin = me.item == null ? RANGE_MIN_DEFAULT_VALUE : me.item.rangeMin;
		rangeMax = me.item == null ? RANGE_MAX_DEFAULT_VALUE : me.item.rangeMax;
		multi = me.item == null ? MULTI_DEFAULT_VALUE : me.item.multi;
	}
	
	/**
	 * meにセットされたitemのターゲット選択メソッドを実行する
	 */
	@Override
	public boolean playerTarget() {
		return me.item.playerTarget();
	}
	
	/**
	 * meにセットされたitemのexecuteメソッドを実行する
	 */
	@Override
	public void execute() {
		me.item.execute();
	}
}
