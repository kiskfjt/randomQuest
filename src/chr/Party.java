package chr;

import java.util.ArrayList;

import chr.jobs.BraveChr;
import chr.jobs.PriestChr;
import item.ItemMedHerb;
import others.IO;

public class Party {
	public static final int PARTY_KIND_ALLY = 0;
	public static final int PARTY_KIND_ENEMY = 1;
	
	private final int MAX_ITEM_NUMBER = 10;
	
	public ArrayList<Chr> member;
	public Party enemy;
	public int pKind;
	
	public Party(String[] name, int partyKind) {
		member = new ArrayList<>();
		for (int i = 0; i < name.length; i++) {
			Chr chr = null;
			// ジョブをランダム選択する
			chr = selectJob(name[i]);
			
			// どうぐをランダム選択する
			selectItem(chr);
			
			
			if (partyKind == PARTY_KIND_ALLY) {
				chr.setToPC();
				pKind = PARTY_KIND_ALLY;
			} else {
				pKind = PARTY_KIND_ENEMY;
			}
			
			member.add(chr);
			chr.party = this;
			
			
			//System.out.println(member.get(i).name);
			//System.out.println(battle.party.member.get(i).ATK);
		}
	}
	
	public Party(String[] name, int partyKind, Chr[] enemyChr) {
		member = new ArrayList<>();
		Chr chr = null;
		for (int i = 0; i < name.length; i++) {
			chr = enemyChr[i];
			chr.name = name[i];
			
			member.add(chr);
			chr.party = this;
		}
	}
	
	public boolean isZenmetsu() {
		for (Chr c : member) {
			if (c.isAlive()) {
				return false;
			}
		}
		return true;
	}
	
	private Chr selectJob(String name) {
		Chr chr = null;
		int jobNo = IO.randomNum(1);// ジョブ数-1を記入
		switch (jobNo) {
		case 0:
			chr = new BraveChr(name);
			break;
		case 1:
			chr = new PriestChr(name);
			break;
		}
		return chr;
	}
	
	private void selectItem(Chr chr) {
		for (int i = 0; i < MAX_ITEM_NUMBER; i++) {
			int itemNo = IO.randomNum(0);
			
			switch (itemNo) {
			case 0:
			    chr.items.add(new ItemMedHerb(chr));
			    break;
			}
		}
	}
}
