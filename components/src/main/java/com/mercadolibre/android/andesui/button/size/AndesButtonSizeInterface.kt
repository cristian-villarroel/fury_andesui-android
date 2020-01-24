package com.mercadolibre.android.andesui.button.size

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Bitmap
import android.graphics.PorterDuff
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.button.AndesButton
import com.mercadolibre.android.andesui.button.factory.IconConfig
import com.mercadolibre.android.andesui.button.hierarchy.AndesButtonHierarchyInterface

const val DEFAULT_ICON_COLOR: Int = 0

/**
 * Defines all size related properties that an [AndesButton] needs to be drawn properly.
 * Those properties change depending on the size of the button.
 *
 */
internal interface AndesButtonSizeInterface {
    /**
     * Returns a [Float] representing the text size to be used.
     *
     * @param context needed for accessing some resources.
     * @return a [Float] representing the text size to be used.
     */
    fun textSize(context: Context): Float

    /**
     * Returns a [Float] representing the height size to be used.
     *
     * @param context needed for accessing some resources.
     * @return a [Float] representing the height size to be used.
     */
    fun height(context: Context): Float

    /**
     * Returns an [Int] representing the left margin to be used.
     *
     * @param context needed for accessing some resources.
     * @return an [Int] representing the left margin to be used.
     */
    fun textLeftMargin(context: Context): Int

    /**
     * Returns an [Int] representing the right margin to be used.
     *
     * @param context needed for accessing some resources.
     * @return an [Int] representing the right margin to be used.
     */
    fun textRightMargin(context: Context): Int

    /**
     * Returns the left margin that the icon should have in its left side.
     * 0 by default.
     *
     * @param context needed for accessing some dimen resources.
     */
    fun leftIconLeftMargin(context: Context) = 0

    /**
     * Returns the left margin that the icon should have in its right side.
     * 0 by default.
     *
     * @param context needed for accessing some dimen resources.
     */
    fun leftIconRightMargin(context: Context) = 0

    /**
     * Returns the right margin that the icon should have in its left side.
     * 0 by default.
     *
     * @param context needed for accessing some dimen resources.
     */
    fun rightIconLeftMargin(context: Context) = 0

    /**
     * Returns the right margin that the icon should have in its right side.
     * 0 by default.
     *
     * @param context needed for accessing some dimen resources.
     */
    fun rightIconRightMargin(context: Context) = 0

    fun lateralPadding(context: Context): Int

    /**
     * Returns a [Float] representing the corner radius to be used.
     *
     * @param context needed for accessing some resources.
     * @return a [Float] representing the corner radius to be used.
     */
    fun cornerRadius(context: Context): Float

    /**
     * Returns an [IconConfig] that holds the icons to be used in the button.
     *
     *
     * @param hierarchy determined hierarchy of the button: Needed because hierarchy provides the color of the icon.
     * @param leftIcon determined icon of the button. Needed because this is the icon to be resized and tinted to be used properly inside the button.
     * @param rightIcon determined icon of the button. Needed because this is the icon to be resized and tinted to be used properly inside the button.
     * @param context needed for accessing some resources.
     * @return an [IconConfig] that holds the icons to be used in the button.
     */
    fun iconConfig(hierarchy: AndesButtonHierarchyInterface, leftIcon: Drawable?, rightIcon: Drawable?, context: Context): IconConfig?

    /**
     * Checks if the current size can display an icon.
     * Returns false by default.
     *
     * @return true if can display an icon, false otherwise.
     */
    fun canDisplayIcon(): Boolean = false
}

/**
 * Implementation of [AndesButtonSizeInterface] that returns the required data but personalized for the Large Size,
 * according to Andes specifications.
 *
 */
internal class AndesLargeButtonSize : AndesButtonSizeInterface {

    override fun textSize(context: Context) = context.resources.getDimension(R.dimen.andesui_text_size_button_large)
    override fun height(context: Context) = context.resources.getDimension(R.dimen.andesui_button_height_large)
    override fun textLeftMargin(context: Context) = context.resources.getDimension(R.dimen.andesui_button_margin_large).toInt()
    override fun textRightMargin(context: Context) = context.resources.getDimension(R.dimen.andesui_button_margin_large).toInt()
    override fun leftIconRightMargin(context: Context) = context.resources.getDimension(R.dimen.andesui_button_left_icon_right_margin).toInt()
    override fun rightIconLeftMargin(context: Context) = context.resources.getDimension(R.dimen.andesui_button_right_icon_left_margin).toInt()
    override fun lateralPadding(context: Context) = context.resources.getDimension(R.dimen.andesui_button_lateral_padding_large).toInt()
    override fun cornerRadius(context: Context) = context.resources.getDimension(R.dimen.andesui_button_border_radius_large)
    override fun iconConfig(hierarchy: AndesButtonHierarchyInterface, leftIcon: Drawable?, rightIcon: Drawable?, context: Context): IconConfig? {
        if (leftIcon != null) { //Ignoring if rightIcon is also non null: Left icon has higher precedence than right
            val leftBitmapDrawable = buildScaledBitmapDrawable(leftIcon as BitmapDrawable, context, hierarchy.iconColor(context))
            return IconConfig(leftIcon = leftBitmapDrawable, rightIcon = null)
        }

        if (rightIcon != null) {
            val rightBitmapDrawable = buildScaledBitmapDrawable(rightIcon as BitmapDrawable, context, hierarchy.iconColor(context))
            return IconConfig(leftIcon = null, rightIcon = rightBitmapDrawable)
        }

        return null //No icon has been specified
    }

    override fun canDisplayIcon() = true
}

/**
 * Implementation of [AndesButtonSizeInterface] that returns the required data but personalized for the Medium Size,
 * according to Andes specifications.
 *
 */
internal class AndesMediumButtonSize : AndesButtonSizeInterface {
    override fun textSize(context: Context) = context.resources.getDimension(R.dimen.andesui_text_size_button_medium)
    override fun height(context: Context) = context.resources.getDimension(R.dimen.andesui_button_height_medium)
    override fun textLeftMargin(context: Context) = 0
    override fun textRightMargin(context: Context) = 0
    override fun lateralPadding(context: Context) = context.resources.getDimension(R.dimen.andesui_button_lateral_padding_medium).toInt()
    override fun cornerRadius(context: Context) = context.resources.getDimension(R.dimen.andesui_button_border_radius_medium)
    override fun iconConfig(hierarchy: AndesButtonHierarchyInterface, leftIcon: Drawable?, rightIcon: Drawable?, context: Context): Nothing? = null
}

/**
 * Implementation of [AndesButtonSizeInterface] that returns the required data but personalized for the Small Size,
 * according to Andes specifications.
 *
 */
internal class AndesSmallButtonSize : AndesButtonSizeInterface {
    override fun textSize(context: Context) = context.resources.getDimension(R.dimen.andesui_text_size_button_small)
    override fun height(context: Context) = context.resources.getDimension(R.dimen.andesui_button_height_small)
    override fun textLeftMargin(context: Context) = 0
    override fun textRightMargin(context: Context) = 0
    override fun lateralPadding(context: Context) = context.resources.getDimension(R.dimen.andesui_button_lateral_padding_small).toInt()
    override fun cornerRadius(context: Context) = context.resources.getDimension(R.dimen.andesui_button_border_radius_small)
    override fun iconConfig(hierarchy: AndesButtonHierarchyInterface, leftIcon: Drawable?, rightIcon: Drawable?, context: Context): Nothing? = null
}

/**
 * Receives a [BitmapDrawable] which will suffer some look overhauling that includes scaling and tinting.
 * When the polishing ends, it will return a new [BitmapDrawable].
 * Size of the icon is based on Andes Specification.
 *
 * @param image image of the icon. Ok: The icon.
 * @param context needed for accessing some resources like size, you know.
 * @param color we said we will be tinting the icon and this is the color list (color pends on the state). For API<21 the color corresponding to state_enabled will be used.
 * @return a complete look overhauled [BitmapDrawable].
 */
private fun buildScaledBitmapDrawable(image: BitmapDrawable, context: Context, color: ColorStateList? = null): BitmapDrawable {
    val scaledBitmap = Bitmap.createScaledBitmap(
            image.bitmap,
            context.resources.getDimensionPixelSize(R.dimen.andesui_button_icon_width),
            context.resources.getDimensionPixelSize(R.dimen.andesui_button_icon_height),
            true)
    return BitmapDrawable(context.resources, scaledBitmap)
            .apply {
                color?.let {
                    if (isLollipopOrNewer()) {
                        setTintMode(PorterDuff.Mode.SRC_IN)
                        setTintList(it)
                    } else {
                        setColorFilter(it.getColorForState(intArrayOf(android.R.attr.state_enabled), DEFAULT_ICON_COLOR), PorterDuff.Mode.SRC_IN)
                    }
                }
            }
}

fun isLollipopOrNewer() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP