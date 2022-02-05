package thelm.jaopca.compat.tconstruct;

import java.util.Arrays;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;

import com.google.gson.JsonElement;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.Tag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidStack;
import slimeknights.mantle.recipe.helper.ItemOutput;
import slimeknights.mantle.recipe.ingredient.FluidIngredient;
import thelm.jaopca.compat.tconstruct.recipes.CastingBasinRecipeSerializer;
import thelm.jaopca.compat.tconstruct.recipes.CastingTableRecipeSerializer;
import thelm.jaopca.compat.tconstruct.recipes.MeltingRecipeSerializer;
import thelm.jaopca.compat.tconstruct.recipes.OreMeltingRecipeSerializer;
import thelm.jaopca.utils.ApiImpl;
import thelm.jaopca.utils.MiscHelper;

public class TConstructHelper {

	public static final TConstructHelper INSTANCE = new TConstructHelper();

	private TConstructHelper() {}

	public FluidIngredient getFluidIngredient(Object obj, int amount) {
		if(obj instanceof Supplier<?>) {
			return getFluidIngredient(((Supplier<?>)obj).get(), amount);
		}
		else if(obj instanceof FluidIngredient) {
			return (FluidIngredient)obj;
		}
		else if(obj instanceof String) {
			return FluidIngredient.of(MiscHelper.INSTANCE.getFluidTag(new ResourceLocation((String)obj)), amount);
		}
		else if(obj instanceof ResourceLocation) {
			return FluidIngredient.of(MiscHelper.INSTANCE.getFluidTag((ResourceLocation)obj), amount);
		}
		else if(obj instanceof Tag<?>) {
			return FluidIngredient.of((Tag<Fluid>)obj, amount);
		}
		else if(obj instanceof FluidStack) {
			return FluidIngredient.of((FluidStack)obj);
		}
		else if(obj instanceof FluidStack[]) {
			return FluidIngredient.of(Arrays.stream((FluidStack[])obj).map(FluidIngredient::of).toArray(FluidIngredient[]::new));
		}
		else if(obj instanceof Fluid) {
			return FluidIngredient.of((Fluid)obj, amount);
		}
		else if(obj instanceof Fluid[]) {
			return FluidIngredient.of(Arrays.stream((Fluid[])obj).map(g->FluidIngredient.of(g, amount)).toArray(FluidIngredient[]::new));
		}
		else if(obj instanceof JsonElement) {
			return FluidIngredient.deserialize((JsonElement)obj, "ingredient");
		}
		return FluidIngredient.EMPTY;
	}

	public ItemOutput getItemOutput(Object obj, int count) {
		if(obj instanceof Supplier<?>) {
			return getItemOutput(((Supplier<?>)obj).get(), count);
		}
		else if(obj instanceof ItemOutput) {
			return ((ItemOutput)obj);
		}
		else if(obj instanceof ItemStack) {
			return ItemOutput.fromStack((ItemStack)obj);
		}
		else if(obj instanceof ItemLike) {
			return ItemOutput.fromStack(new ItemStack((ItemLike)obj, count));
		}
		else if(obj instanceof String) {
			return ItemOutput.fromTag(MiscHelper.INSTANCE.getItemTag(new ResourceLocation((String)obj)), count);
		}
		else if(obj instanceof ResourceLocation) {
			return ItemOutput.fromTag(MiscHelper.INSTANCE.getItemTag((ResourceLocation)obj), count);
		}
		else if(obj instanceof Tag<?>) {
			return ItemOutput.fromTag((Tag<Item>)obj, count);
		}
		return ItemOutput.fromStack(ItemStack.EMPTY);
	}

	public boolean registerOreMeltingRecipe(ResourceLocation key, String group, Object input, Object output, int outputAmount, int rate, ToIntFunction<FluidStack> temperature, ToIntFunction<FluidStack> time, Object... byproducts) {
		return ApiImpl.INSTANCE.registerRecipe(key, new OreMeltingRecipeSerializer(key, group, input, output, outputAmount, rate, temperature, time, byproducts));
	}

	public boolean registerOreMeltingRecipe(ResourceLocation key, Object input, Object output, int outputAmount, int rate, ToIntFunction<FluidStack> temperature, ToIntFunction<FluidStack> time, Object... byproducts) {
		return ApiImpl.INSTANCE.registerRecipe(key, new OreMeltingRecipeSerializer(key, input, output, outputAmount, rate, temperature, time, byproducts));
	}

	public boolean registerMeltingRecipe(ResourceLocation key, String group, Object input, Object output, int outputAmount, ToIntFunction<FluidStack> temperature, ToIntFunction<FluidStack> time, Object... byproducts) {
		return ApiImpl.INSTANCE.registerRecipe(key, new MeltingRecipeSerializer(key, group, input, output, outputAmount, temperature, time, byproducts));
	}

	public boolean registerMeltingRecipe(ResourceLocation key, Object input, Object output, int outputAmount, ToIntFunction<FluidStack> temperature, ToIntFunction<FluidStack> time, Object... byproducts) {
		return ApiImpl.INSTANCE.registerRecipe(key, new MeltingRecipeSerializer(key, input, output, outputAmount, temperature, time, byproducts));
	}

	public boolean registerCastingTableRecipe(ResourceLocation key, String group, Object cast, Object input, int inputAmount, Object output, int outputCount, ToIntFunction<FluidStack> time, boolean consumeCast, boolean switchSlots) {
		return ApiImpl.INSTANCE.registerRecipe(key, new CastingTableRecipeSerializer(key, group, cast, input, inputAmount, output, outputCount, time, consumeCast, switchSlots));
	}

	public boolean registerCastingTableRecipe(ResourceLocation key, Object cast, Object input, int inputAmount, Object output, int outputCount, ToIntFunction<FluidStack> time, boolean consumeCast, boolean switchSlots) {
		return ApiImpl.INSTANCE.registerRecipe(key, new CastingTableRecipeSerializer(key, cast, input, inputAmount, output, outputCount, time, consumeCast, switchSlots));
	}

	public boolean registerCastingBasinRecipe(ResourceLocation key, String group, Object cast, Object input, int inputAmount, Object output, int outputCount, ToIntFunction<FluidStack> time, boolean consumeCast, boolean switchSlots) {
		return ApiImpl.INSTANCE.registerRecipe(key, new CastingBasinRecipeSerializer(key, group, cast, input, inputAmount, output, outputCount, time, consumeCast, switchSlots));
	}

	public boolean registerCastingBasinRecipe(ResourceLocation key, Object cast, Object input, int inputAmount, Object output, int outputCount, ToIntFunction<FluidStack> time, boolean consumeCast, boolean switchSlots) {
		return ApiImpl.INSTANCE.registerRecipe(key, new CastingBasinRecipeSerializer(key, cast, input, inputAmount, output, outputCount, time, consumeCast, switchSlots));
	}
}