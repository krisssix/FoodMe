package cz.mendelu.xnazarja.va2.foodMe.ui.activities

import android.Manifest
import android.os.Bundle
import androidx.compose.ui.graphics.Color
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.github.appintro.AppIntro
import com.github.appintro.AppIntroFragment
import com.github.appintro.AppIntroPageTransformerType
import cz.mendelu.xnazarja.va2.foodMe.R
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class AppIntroActivity : AppIntro(){

    private val viewModel: AppIntroViewModel by viewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isWizardMode = true

        setIndicatorColor(
            selectedIndicatorColor = getColor(R.color.black),
            unselectedIndicatorColor = getColor(R.color.markerRed)
        )

        setNextArrowColor(R.color.black)
        setBackArrowColor(R.color.black)
        setSeparatorColor(R.color.black)
        setColorDoneText(R.color.black)
        setTransformer(AppIntroPageTransformerType.Parallax())

        addSlide(
            AppIntroFragment.createInstance(
                titleTypefaceFontRes = R.font.urbanist_semi_bold,
                descriptionTypefaceFontRes = R.font.urbanist_regular,
                title = getString(R.string.intro1T),
                description = getString(R.string.intro1D),
                backgroundColorRes = R.color.white,
                imageDrawable = R.drawable.logo,
                titleColorRes = R.color.black,
                descriptionColorRes = R.color.black
            ))
        addSlide(AppIntroFragment.createInstance(
            titleTypefaceFontRes = R.font.urbanist_semi_bold,
            descriptionTypefaceFontRes = R.font.urbanist_regular,
            title = getString(R.string.intro2T),
            description = getString(R.string.intro2D),
            backgroundColorRes = R.color.white,
            imageDrawable = R.drawable.nearby,
            titleColorRes = R.color.black,
            descriptionColorRes = R.color.black
        ))
        addSlide(AppIntroFragment.createInstance(
            titleTypefaceFontRes = R.font.urbanist_semi_bold,
            descriptionTypefaceFontRes = R.font.urbanist_regular,
            title = getString(R.string.intro3T),
            description = getString(R.string.intro3D),
            imageDrawable = R.drawable.redblackpresent,
            backgroundColorRes = R.color.white,
            titleColorRes = R.color.black,
            descriptionColorRes = R.color.black
        ))
        addSlide(AppIntroFragment.createInstance(
            titleTypefaceFontRes = R.font.urbanist_semi_bold,
            descriptionTypefaceFontRes = R.font.urbanist_regular,
            title = getString(R.string.intro4T),
            description = getString(R.string.intro4D),
            backgroundColorRes = R.color.white,
            imageDrawable = R.drawable.cameraicon,
            titleColorRes = R.color.black,
            descriptionColorRes = R.color.black
        ))
        addSlide(AppIntroFragment.createInstance(
            titleTypefaceFontRes = R.font.urbanist_semi_bold,
            descriptionTypefaceFontRes = R.font.urbanist_regular,
            title = getString(R.string.intro5T),
            description = getString(R.string.intro5D),
            imageDrawable = R.drawable.thatall,
            backgroundColorRes = R.color.white,
            titleColorRes = R.color.black,
            descriptionColorRes = R.color.black
        ))


        askForPermissions(
            permissions = arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            ),
            slideNumber = 3,
            required = true
        )

        askForPermissions(
            permissions = arrayOf(Manifest.permission.CAMERA),
            slideNumber = 4,
            required = true
        )

    }

    override fun onDonePressed(currentFragment: Fragment?) {
        super.onDonePressed(currentFragment)
        continueToMainActivity()
    }

    private fun continueToMainActivity() {
        lifecycleScope.launch {
            viewModel.setFirstRun()
        }.invokeOnCompletion {
            finish()
        }
    }

}