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
import com.esri.arcgisruntime.mapping.view.Graphic
import com.esri.arcgisruntime.mapping.view.GraphicsOverlay
import com.esri.arcgisruntime.mapping.view.SketchEditor
import com.esri.arcgisruntime.symbology.SimpleFillSymbol
import com.esri.arcgisruntime.symbology.SimpleLineSymbol
import com.esri.arcgisruntime.symbology.SimpleMarkerSymbol
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
        val map = ArcGISMap(BasemapStyle.ARCGIS_IMAGERY)

        // set the map to be displayed in the layout's MapView
        binding.mapView.map = map

        // set the viewpoint, Viewpoint(latitude, longitude, scale)
        binding.mapView.setViewpoint(Viewpoint(14.0566957, 77.3593152, 3000.0))


       /* if(binding.mapView.map!=null){
            addSkechEditor()

            editButton()

            undoButtonClick()

            RedoButtonClick()
        }*/

    }

    private fun addGraphics() {

        // create a graphics overlay and add it to the map view
        var graphicsOverlay = GraphicsOverlay()
        /*binding.mapView.graphicsOverlays.add(graphicsOverlay)

        graphicsOverlay = GraphicsOverlay()*/

// create a map point for the Santa Monica pier

// create a map point for the Santa Monica pier
        val pierPoint = Point(77.3593152, 14.0566957, SpatialReferences.getWgs84())

// create a red (0xFFFF0000) circle simple marker symbol

// create a red (0xFFFF0000) circle simple marker symbol
        val redCircleSymbol = SimpleMarkerSymbol(SimpleMarkerSymbol.Style.CIRCLE, -0x10000, 10F)

// create a graphic from the point and symbol

// create a graphic from the point and symbol
        val pierGraphic = Graphic(pierPoint, redCircleSymbol)

// add the graphic to the graphics overlay

// add the graphic to the graphics overlay
        graphicsOverlay.graphics.add(pierGraphic)

// add graphics overlay to the map view's graphics overlay collection

// add graphics overlay to the map view's graphics overlay collection
        binding.mapView.getGraphicsOverlays().add(graphicsOverlay)


        // create a point collection with a spatial reference, and add five points to it
        val polygonPoints = PointCollection(SpatialReferences.getWgs84()).apply {
            // Point(latitude, longitude)
            add(Point(77.3591336, 14.0570376))
            add(Point(77.3589039, 14.0565828))
            add(Point(77.3594680,14.0563753))
            add(Point(77.3596617, 14.0568588))
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