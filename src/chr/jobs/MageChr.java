package chr.jobs;

import action.ActionBasicAttack;
import action.ActionBasicGuard;
import action.ActionEquipment;
import action.ActionItem;
import action.magics.ActionMagicBounce;
import action.magics.ActionMagicBreakShieldAll;
import action.magics.ActionMagicDemonsLance;
import action.magics.ActionMagicEruption;
import action.magics.ActionMagicFireBall;
import action.magics.ActionMagicIceNeedle;
import action.magics.ActionMagicIceTornado;
import action.magics.ActionMagicMPAbsorb;
import action.magics.ActionMagicRay;
import action.magics.ActionMagicRockLance;
import action.magics.ActionMagicSnooze;
import action.magics.ActionMagicSnoozeAll;
import action.magics.ActionMagicStoneBlast;
import action.magics.ActionMagicStorm;
import action.magics.ActionMagicThunderBlade;
import action.magics.ActionMagicThunderBolt;
import actions.ActionMagicCyclone;
import chr.Chr;

public class MageChr extends Chr {
	
	public MageChr() {
		super(100, 100, 25, 15, 40, 25, 10);
		
		jobName = "魔法使い";
		
		actions.add(new ActionBasicAttack(this));
		actions.add(new ActionBasicGuard(this));
		actions.add(new ActionItem(this));
		actions.add(new ActionEquipment(this));
		
		actions.add(new ActionMagicFireBall(this));
		actions.add(new ActionMagicIceNeedle(this));
		actions.add(new ActionMagicStoneBlast(this));
		actions.add(new ActionMagicStorm(this));
		actions.add(new ActionMagicThunderBolt(this));
		actions.add(new ActionMagicEruption(this));
		actions.add(new ActionMagicIceTornado(this));
		actions.add(new ActionMagicRockLance(this));
		actions.add(new ActionMagicCyclone(this));
		actions.add(new ActionMagicThunderBlade(this));
		actions.add(new ActionMagicRay(this));
		actions.add(new ActionMagicDemonsLance(this));
		actions.add(new ActionMagicBreakShieldAll(this));
		actions.add(new ActionMagicSnooze(this));
		actions.add(new ActionMagicSnoozeAll(this));
		actions.add(new ActionMagicMPAbsorb(this));
		actions.add(new ActionMagicBounce(this));
	}
	
	@Override
	public boolean nonPlayerCommand() {
		// TODO 自動生成されたメソッド・スタブ
		return false;
	}

}
