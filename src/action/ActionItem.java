package action;

import chr.Chr;

public class ActionItem extends Action {

	public ActionItem(Chr me) {
		super(me);
		name = "どうぐ";
		rangeMin = me.item == null ? Action.RANGE_MIN_DEFAULT_VALUE : me.item.rangeMin;
		rangeMax = me.item == null ? Action.RANGE_MAX_DEFAULT_VALUE : me.item.rangeMax;
		multi = me.item == null ? Action.MULTI_DEFAULT_VALUE : me.item.multi;
		successRate = me.item == null ? Action.SUCCESS_RATE_DEFAULT : me.item.successRate;
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
