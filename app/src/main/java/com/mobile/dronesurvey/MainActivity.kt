package com.mobile.dronesurvey

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.esri.arcgisruntime.ArcGISRuntimeEnvironment
import com.esri.arcgisruntime.geometry.Point
import com.esri.arcgisruntime.geometry.PointCollection
import com.esri.arcgisruntime.geometry.Polygon
import com.esri.arcgisruntime.geometry.SpatialReferences
import com.esri.arcgisruntime.mapping.ArcGISMap
import com.esri.arcgisruntime.mapping.BasemapStyle
import com.esri.arcgisruntime.mapping.Viewpoint
import com.esri.arcgisruntime.mapping.view.*
import com.esri.arcgisruntime.symbology.SimpleFillSymbol
import com.esri.arcgisruntime.symbology.SimpleLineSymbol
import com.mobile.dronesurvey.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding
    private lateinit var mSketchEditor: SketchEditor
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setApiKeyForApp()

        setupMap()

        addGraphics()

        backbutton()


    }

    private fun backbutton() {
        binding.backArrowLayout.title.text = resources.getString(R.string.app_name)
        binding.backArrowLayout.backArrow.setOnClickListener {
            finish()
        }
    }

    /*  private fun undoButtonClick() {
          binding.undoButton.setOnClickListener {
              if (mSketchEditor.canUndo()){
                  mSketchEditor.undo()
              }
          }
      }

      private fun RedoButtonClick() {
          binding.redoButton.setOnClickListener {
              if (mSketchEditor.canRedo()){
                  mSketchEditor.redo()
              }
          }
      }
      private fun addSkechEditor() {
           mSketchEditor = SketchEditor()
          binding.mapView.sketchEditor = mSketchEditor

      }
      private fun editButton() {
          binding.polylineButton.setOnClickListener {
              createModePolyLine()
          }
      }

      private fun createModePolyLine() {
          binding.polylineButton.isSelected = false
          binding.polylineButton.isSelected = true
          mSketchEditor.start(SketchCreationMode.POLYLINE)
      }*/

    private fun setApiKeyForApp(){
        // set your API key
        // Note: it is not best practice to store API keys in source code. The API key is referenced
        // here for the convenience of this tutorial.

        ArcGISRuntimeEnvironment.setApiKey("AAPK70cf10a3ed034554a34e08af2dca3c2bkkjTXZI_INhpTdGil7vIxN4aioK8AtJihyOsX8E2HzGKBkGMGAmq1Ih3A9Wm5HIv")


    }

    private fun setupMap() {
        // create a map with the BasemapStyle streets
        val map = ArcGISMap(BasemapStyle.ARCGIS_TOPOGRAPHIC)

        // set the map to be displayed in the layout's MapView
        binding.mapView.map = map

        // set the viewpoint, Viewpoint(latitude, longitude, scale)
        binding.mapView.setViewpoint(Viewpoint(34.0270, -118.8050, 72000.0))



       /* if(binding.mapView.map!=null){
            addSkechEditor()

            editButton()

            undoButtonClick()

            RedoButtonClick()
        }*/

    }

    private fun addGraphics() {

        // create a graphics overlay and add it to the map view
        val graphicsOverlay = GraphicsOverlay()
        binding.mapView.graphicsOverlays.add(graphicsOverlay)

        // create a point collection with a spatial reference, and add five points to it
        val polygonPoints = PointCollection(SpatialReferences.getWgs84()).apply {
            // Point(latitude, longitude)
            add(Point(-118.8189, 34.0137))
            add(Point(-118.8067, 34.0215))
            add(Point(-118.7914, 34.0163))
            add(Point(-118.7959, 34.0085))
            add(Point(-118.8085, 34.0035))
        }
        val blueOutlineSymbol = SimpleLineSymbol(SimpleLineSymbol.Style.SOLID, -0xff9c01, 2f)


        // create a polygon geometry from the point collection
        val polygon = Polygon(polygonPoints)

        // create an orange fill symbol with 20% transparency and the blue simple line symbol
        val polygonFillSymbol =
            SimpleFillSymbol(SimpleFillSymbol.Style.SOLID, -0x7f00a8cd, blueOutlineSymbol)
          //  SimpleFillSymbol(SimpleFillSymbol.Style.SOLID, resources.getColor(R.color.colorPrimary), blueOutlineSymbol)


        // create a polygon graphic from the polygon geometry and symbol
        val polygonGraphic = Graphic(polygon, polygonFillSymbol)
        // add the polygon graphic to the graphics overlay
        graphicsOverlay.graphics.add(polygonGraphic)


    }

    override fun onPause() {
        binding.mapView.pause()
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
        binding.mapView.resume()
    }

    override fun onDestroy() {
        binding.mapView.dispose()
        super.onDestroy()
    }


}