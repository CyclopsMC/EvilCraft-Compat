package thaumcraft.api;

import net.minecraft.item.ItemStack;
import thaumcraft.api.aspects.AspectList;


/**
 * @author Azanor
 *
 *
 * IMPORTANT: If you are adding your own aspects to items it is a good idea to do it AFTER Thaumcraft adds its aspects, otherwise odd things may happen.
 *
 */
public class ThaumcraftApi {
	
	
	
	//ASPECTS////////////////////////////////////////
	
	/**
	 * Used to assign apsects to the given item/block. Here is an example of the declaration for cobblestone:<p>
	 * <i>ThaumcraftApi.registerObjectTag(new ItemStack(Blocks.COBBLESTONE), (new AspectList()).add(Aspect.ENTROPY, 1).add(Aspect.EARTH, 1));</i>
	 * @param item the item passed. Pass OreDictionary.WILDCARD_VALUE if all damage values of this item/block should have the same aspects
	 * @param aspects A ObjectTags object of the associated aspects
	 */
	public static void registerObjectTag(ItemStack item, AspectList aspects) {

	}
	
	/**
	 * Used to assign apsects to the given ore dictionary item. 
	 * @param oreDict the ore dictionary name
	 * @param aspects A ObjectTags object of the associated aspects
	 */
	public static void registerObjectTag(String oreDict, AspectList aspects) {

	}

	
	/**
	 * This is used to add aspects to entities which you can then scan using a thaumometer.
	 * Also used to calculate vis drops from mobs.
	 * @param entityName
	 * @param aspects
	 * @param nbt you can specify certain nbt keys and their values 
	 * 			  to differentiate between mobs. <br>For example the normal and wither skeleton:
	 * 	<br>ThaumcraftApi.registerEntityTag("Skeleton", (new AspectList()).add(Aspect.DEATH, 5));
	 * 	<br>ThaumcraftApi.registerEntityTag("Skeleton", (new AspectList()).add(Aspect.DEATH, 8), new NBTTagByte("SkeletonType",(byte) 1));
	 */
	public static void registerEntityTag(String entityName, AspectList aspects, EntityTagsNBT... nbt ) {

	}
	
	// LOOT BAGS 
		
	/**
	 * Used to add possible loot to treasure bags. As a reference, the weight of gold coins are 2000 
	 * and a diamond is 50.
	 * The weights are the same for all loot bag types - the only difference is how many items the bag
	 * contains.
	 * @param item
	 * @param weight
	 * @param bagTypes array of which type of bag to add this loot to. Multiple types can be specified
	 * 0 = common, 1 = uncommon, 2 = rare
	 */
	public static void addLootBagItem(ItemStack item, int weight, int... bagTypes) {

	}

	public static class EntityTagsNBT {
		public EntityTagsNBT(String name, Object value) {
			this.name = name;
			this.value = value;
		}
		public String name;
		public Object value;
	}
	
}
