package chr.jobs;

import java.util.ArrayList;

import action.ActionBasicAttack;
import action.ActionBasicGuard;
import action.ActionEquipment;
import action.ActionItem;
import action.ActionMagic;
import action.magics.ActionMagicHeal;
import action.magics.ActionMagicRevive;
import chr.Chr;
import others.IO;

public class PriestChr extends Chr {
	private final int A_ATTACK = 0;
	private final int A_GUARD = 1;
	private final int A_HEAL = 2;
	private final int A_ITEM = 3;
	private final int A_EQUIP = 3;
	private final int MAX_HP = 9999;
	public PriestChr(String name) {
		super(name, 100, 100, 30, 20, 50, 30, 15);
		
		jobName = "そうりょ";
		
		actions.add(new ActionBasicAttack(this));
		actions.add(new ActionBasicGuard(this));
		actions.add(new ActionMagicHeal(this));
		actions.add(new ActionMagicRevive(this));
		actions.add(new ActionItem(this));
		actions.add(new ActionEquipment(this));
		
		setToNPC();
	}
	
	public void setToNPC() {
		command = this::nonPlayerCommand;
		actions.get(A_ATTACK).target = this::attackTarget;
		actions.get(A_GUARD).target = this::guardTarget;
		actions.get(A_HEAL).target = this::healTarget;
	}
	
	/**
	 * NPC「そうりょ」の行動選択メソッド
	 * MPがヒールの消費MPよりも多いとき、かつ、
	 * 敵パーティーメンバーのHPがmaxHPの半分以下の時は「ヒール」優先
	 * 回復をしないときは、「こうげき」と「ぼうぎょ」からランダム選択
	 */
	public boolean nonPlayerCommand() {
		ArrayList<Chr> list = new ArrayList<>(party.member);
		for (Chr c : list) {
			ActionMagic actionMagic = (ActionMagic) actions.get(A_HEAL);
			if (MP >= actionMagic.MPCons && c.HP <= c.maxHP / 2) {
				action = actions.get(A_HEAL);
				// action.target = this::healTarget;
			} else {
				int actionNum = IO.randomNum(A_GUARD);
				if (actionNum == A_ATTACK) {
					action = actions.get(A_ATTACK);
					// action.target = this::attackTarget;
				} else if (actionNum == A_GUARD) {
					action = actions.get(A_GUARD);
					// action.target = this::guardTarget;
				}
			}
		}
		return action.target.get();
	}
	
	/**
	 * NPC「そうりょ」の「こうげき」のターゲット決定メソッド
	 * 敵パーティーからランダムに1体選択
	 */
	public boolean attackTarget() {
		ArrayList<Chr> list = new ArrayList<>(party.enemy.member);
	    int targetNum = 0;
	    Chr target = null;
	    while (true) {
	    	targetNum = IO.randomNum(list.size() - 1);
	    	target = list.get(targetNum);
	    	if (target.isAlive()) {
	    		this.targets.add(target);
	    		break;
	    	}
	    }
	    return true;
	}
	
	public boolean guardTarget() {
		targets.clear();
		return true;
	}
	
	/**
	 * NPC「そうりょ」の回復呪文「ヒール」のターゲット決定メソッド
	 * 一番HPの低い味方を1体選ぶ
	 * 最低HP同値の場合はmemberリストの番号の小さいほうが優先
	 */
	public boolean healTarget() {
		int targetNum = checkLowestHP();
		Chr target = party.member.get(targetNum);
		targets.add(target);
		return true;
	}
	
	public int checkLowestHP() {
		ArrayList<Chr> list = new ArrayList<>(party.member);
		int minHP = MAX_HP;
		int targetNum = 0;
		for (int i = 0; i < list.size(); i++) {
			Chr c = list.get(i);
			if (c.isAlive() && c.HP < minHP) {
				minHP = c.HP;
				targetNum = i;
			}
		}
		return targetNum;
	}
}
