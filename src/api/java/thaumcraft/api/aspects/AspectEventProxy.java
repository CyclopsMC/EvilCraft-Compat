package thaumcraft.api.aspects;

import net.minecraft.item.ItemStack;

public class AspectEventProxy {
	
	/**
	 * Used to assign apsects to the given item/block. Here is an example of the declaration for cobblestone:<p>
	 * <i>event.registerObjectTag(new ItemStack(Blocks.COBBLESTONE), (new AspectList()).add(Aspect.ENTROPY, 1).add(Aspect.EARTH, 1));</i>
	 * @param item the item passed. Pass OreDictionary.WILDCARD_VALUE if all damage values of this item/block should have the same aspects
	 * @param aspects A ObjectTags object of the associated aspects
	 */
	public void registerObjectTag(ItemStack item, AspectList aspects) {

	}	
	
	/**
	 * Used to assign apsects to the given ore dictionary item. 
	 * @param oreDict the ore dictionary name
	 * @param aspects A ObjectTags object of the associated aspects
	 */
	public void registerObjectTag(String oreDict, AspectList aspects) {

	}
	
	/**
	 * Used to assign aspects to the given item/block. 
	 * Attempts to automatically generate aspect tags by checking registered recipes.
	 * Here is an example of the declaration for pistons:<p>
	 * <i>event.registerComplexObjectTag(new ItemStack(Blocks.PISTON), (new AspectList()).add(Aspect.MECHANISM, 2).add(Aspect.MOTION, 4));</i>
	 * IMPORTANT - this should only be used if you are not happy with the default aspects the object would be assigned.
	 * @param item, pass OreDictionary.WILDCARD_VALUE to meta if all damage values of this item/block should have the same aspects
	 * @param aspects A ObjectTags object of the associated aspects
	 */
	public void registerComplexObjectTag(ItemStack item, AspectList aspects ) {

	}
	
	
	/**
	 * Used to assign aspects to the given ore dictionary item. 
	 * Attempts to automatically generate aspect tags by checking registered recipes.
	 * IMPORTANT - this should only be used if you are not happy with the default aspects the object would be assigned.
	 * @param oreDict the ore dictionary name
	 * @param aspects A ObjectTags object of the associated aspects
	 */
	public void registerComplexObjectTag(String oreDict, AspectList aspects) {

	}

}
