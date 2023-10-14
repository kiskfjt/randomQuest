package others;

import java.util.ArrayList;
import java.util.Scanner;

import action.Action;
import action.ActionBasicAttack;
import action.ActionBasicGuard;
import action.ActionEquip;
import action.ActionItem;
import action.ActionMagic;
import action.ActionSkill;
import chr.Chr;
import chr.Party;
import item.Item;

public class IO {
	private static Scanner sc = new Scanner(System.in);
	private static final int ATTACK = 0;
	private static final int SKILL = 1;
	private static final int MAGIC = 2;
	private static final int ITEM = 3;
	private static final int GUARD = 4;
	private static final int EQUIP = 5;
	

	public static void printStatus(ArrayList<Chr> member) {
		msgln("***********************************");
		msg("%5s", "");
		for (int i = 0; i < member.size(); i++) {
			msg("%5s", member.get(i).name);
		}
		ln();
		msg("%3s", "HP");
		for (int i = 0; i < member.size(); i++) {
			msg("%8d", member.get(i).HP);
		}
		ln();
		msg("%3s", "MP");
		for (int i = 0; i < member.size(); i++) {
			msg("%8d", member.get(i).MP);
		}
		ln();
		msg("%3s", "ATK");
		for (int i = 0; i < member.size(); i++) {
			msg("%8d", member.get(i).ATK);
		}
		ln();
		msg("%3s", "DEF");
		for (int i = 0; i < member.size(); i++) {
			msg("%8d", member.get(i).DEF);
		}
		ln();
		msg("%3s", "DEF");
		for (int i = 0; i < member.size(); i++) {
			msg("%8d", member.get(i).DEF);
		}
		//System.out.println();
		//System.out.printf("%3s", "SPD");
		ln();
		msg("%3s", "job");
		for (int i = 0; i < member.size(); i++) {
			msg("%5s", member.get(i).jobName);
		}

		System.out.println();
		System.out.println("***********************************");
	}
		
	public static Action selectPCAction(Chr me, ArrayList<Action> actions, ArrayList<Item> items) {
		int actionNum = 0;
		int actionSubNum = 0;
		String actionName = "";
		Action action = null;
		ArrayList<Action> selAction = null;
		
		ArrayList<Action> attack = new ArrayList<>();
		ArrayList<Action> skill = new ArrayList<>();
		ArrayList<Action> magic = new ArrayList<>();
		ArrayList<Action> item = new ArrayList<>();
		ArrayList<Action> guard = new ArrayList<>();
		ArrayList<Action> equip = new ArrayList<>();
		
		for (int i = 0; i < actions.size(); i++) {
			action = actions.get(i);
			if (action instanceof ActionBasicAttack) {
				attack.add(action);
			} else if (action instanceof ActionSkill) {
				skill.add(action);
			} else if (action instanceof ActionMagic) {
				magic.add(action);
			} else if (action instanceof ActionItem) {
				item.add(action);
			} else if (action instanceof ActionBasicGuard) {
				guard.add(action);
			} else if (action instanceof ActionEquip) {
				equip.add(action);
			}
		}
		
		
		while (true) {
			msgln("%sの行動の番号を入力", me.name);
			msg("%2d.%s", 0, "こうげき");
			msgln("%2d.%s", 1, "とくぎ　");
			msg("%2d.%s", 2, "まほう　");
			msgln("%2d.%s", 3, "どうぐ　");
			msg("%2d.%s", 4, "ぼうぎょ");
			msgln("%2d.%s", 5, "そうび　");
			
			actionNum = inputNumber(EQUIP);
			
			if (actionNum == ATTACK) {
				selAction = new ArrayList<>(attack);
				actionName = "こうげき";
			} else if (actionNum == SKILL) {
				selAction = new ArrayList<>(skill);
				actionName = "とくぎ";
			} else if (actionNum == MAGIC) {
				selAction = new ArrayList<>(magic);
				actionName = "まほう";
			} else if (actionNum == ITEM) {
				selAction = new ArrayList<>(item);
				actionName = "どうぐ";
			} else if (actionNum == GUARD) {
				selAction = new ArrayList<>(guard);
				actionName = "ぼうぎょ";
			} else if (actionNum == EQUIP) {
				selAction = new ArrayList<>(equip);
				actionName = "そうび";
			}
			
			if (selAction.size() == 0) {
				if (actionNum == ATTACK) {
					msgln("こうげきできない！");
				} else if (actionNum == SKILL) {
					msgln("とくぎを覚えていない！");
				} else if (actionNum == MAGIC) {
					msgln("まほうを覚えていない！");
				} else if (actionNum == GUARD) {
					msgln("ぼうぎょできない！");
				} else if (actionNum == EQUIP) {
					msgln("なにもそうびしていない！");
				}
				continue;
			
			} else if (actionNum == ATTACK || actionNum == GUARD) {
				action = selAction.get(0);
				break;
			
			} else if (actionNum == ITEM) {
				if (items.size() == 0) {
					msgln("どうぐを持っていない！");
					continue;
				} else {
					msgln("%sの使用する%sの番号を入力", me.name, actionName);
					for (int i = 0; i < items.size(); i++) {
						msgln("%d.%s", i, items.get(i).name);
					}
					
					// 使うどうぐの決定
					actionSubNum = inputNumber(items.size() - 1);
					action = selAction.get(0);
					me.item = items.get(actionSubNum);
					break;
				}
				
			} else {
				msgln("%sの使用する%sの番号を入力", me.name, actionName);
				for (int i = 0; i < selAction.size(); i++) {
					msgln("%d.%s", i, selAction.get(i).name);
				}
				
				// 最終的なアクションの決定
				actionSubNum = inputNumber(selAction.size() - 1);
				action = selAction.get(actionSubNum);
				
				// 呪文決定時点でもMP判定を行う
				if (actionNum == MAGIC && ((ActionMagic) action).MPCons > me.MP) {
					msgln("MPがたりない！");
					continue;// TODO:ここで呪文一覧に戻るようにする
				} else {
					break;
				}
			}
		}
		return action;
		
	}
	
	/**
	 * 単体ターゲット選択メソッド
	 * @param memList
	 * @return
	 */
	public static void selectSingleTarget(ArrayList<Chr> memList, Chr me) {
		int targetNum = 0;
		Chr targetChr = null;
		
		while (true) {
			msgln("ターゲットを選択");
			
			for (int i = 0; i < memList.size(); i++) {
				String name = memList.get(i).name;
				IO.msgln(i + ".%s", name);
			}
			targetNum = inputNumber(memList.size() - 1);
			targetChr = memList.get(targetNum);
			if (targetChr.isAlive()) {
				break;
			} else {
				msgln("%sはすでにしんでいる！", targetChr.name);
				continue;
			}
		}
		
		me.targets.add(targetChr);
	}
	
	public static void selectMultiTargets(ArrayList<Chr> memList, Chr me) {
		for (Chr c : memList) {
			if(c.HP > 0) {
				me.targets.add(c);
			}
		}
		
	}

	public static int inputNumber(int max) {
		String str = "";
		int num = 0;
		while (true) {
			try {
				str = sc.nextLine();
				num = Integer.parseInt(str);
				if (num <= max) {
					break;
				} else {
					System.out.println("0から" + max + "までの整数を入力してください");
					continue;
				}
			} catch (NumberFormatException e) {
				System.out.println("0から" + max + "までの整数を入力してください");
				continue;
			}
		}
		return num;
	}
	
	public static void judgeHP(Chr attacker, Chr defender) {
		if (defender.HP <= 0) {
			defender.HP = 0;
			if (attacker.party.pKind == Party.PARTY_KIND_ALLY) {
				System.out.println(defender.name + "をたおした！");
			} else {
				System.out.println(defender.name + "はしんでしまった！");
			}
		}
	}
	
	/**
	 * ランダム整数生成メソッド
	 * minからmaxまでのランダムな整数を返す
	 * @param min
	 * @param max
	 * @return
	 */
	public static int randomNum(int min, int max) {
		int range = max - min + 1;
		return min + (int)(Math.random() * range);
	}
	
	/**
	 * ランダム整数生成メソッド
	 * 0からmaxまでのランダムな整数を返す
	 * @param max
	 * @return
	 */
	public static int randomNum(int max) {
		int range = max + 1;
		return (int)(Math.random() * range);
	}
	
	/**
	 * メッセージを出力するメソッド。
	 * 最後に改行を出力する。
	 * @param fmt 出力フォーマット。
	 * @param v 出力する値。
	 * @see msg
	 */
	public static void msgln(String fmt, Object... v) {
		System.out.print(String.format(fmt + "\n", v));
	}

	/**
	 * メッセージを出力するメソッド。
	 * 最後に改行を出力しない。
	 * @param fmt 出力フォーマット。
	 * @param v 出力する値。
	 * @see msgln
	 */
	public static void msg(String fmt, Object... v) {
		System.out.print(String.format(fmt, v));
	}
	
	/**
	 * 改行のみ行うメソッド
	 */
	public static void ln() {
		System.out.println();
	}
	
	/**
	 * itemsリストから使用したアイテムのインスタンスを削除するメソッド
	 * @param inputNum
	 */
	public static void removeFromItemList(Chr me, Item item) {
		me.items.remove(item);
	}

}
