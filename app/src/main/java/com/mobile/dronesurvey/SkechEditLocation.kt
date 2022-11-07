package com.mobile.dronesurvey

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.esri.arcgisruntime.ArcGISRuntimeEnvironment
import com.esri.arcgisruntime.data.ServiceFeatureTable
import com.esri.arcgisruntime.geometry.*
import com.esri.arcgisruntime.layers.FeatureLayer
import com.esri.arcgisruntime.mapping.ArcGISMap
import com.esri.arcgisruntime.mapping.BasemapStyle
import com.esri.arcgisruntime.mapping.Viewpoint
import com.esri.arcgisruntime.mapping.view.*
import com.esri.arcgisruntime.symbology.SimpleFillSymbol
import com.esri.arcgisruntime.symbology.SimpleLineSymbol
import com.esri.arcgisruntime.symbology.SimpleMarkerSymbol
import com.google.android.material.snackbar.Snackbar


class SkechEditLocation : AppCompatActivity() {
    private val TAG = SkechEditLocation::class.java.simpleName
    private var mPointSymbol: SimpleMarkerSymbol? = null
    private var mLineSymbol: SimpleLineSymbol? = null
    private var mFillSymbol: SimpleFillSymbol? = null
    private lateinit var mMapView: MapView
    private var mSketchEditor: SketchEditor? = null
    private var mGraphicsOverlay: GraphicsOverlay? = null
    private lateinit var mPointButton: ImageButton
    private lateinit var mMultiPointButton: ImageButton
    private lateinit var mPolylineButton: ImageButton
    private lateinit var mPolygonButton: ImageButton
    private lateinit var mFreehandLineButton: ImageButton
    private lateinit var mFreehandPolygonButton: ImageButton
    private lateinit var  map: ArcGISMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.skech_edit_location)

        // authentication with an API key or named user is required to access basemaps and other
        // location services
        ArcGISRuntimeEnvironment.setApiKey("AAPK70cf10a3ed034554a34e08af2dca3c2bkkjTXZI_INhpTdGil7vIxN4aioK8AtJihyOsX8E2HzGKBkGMGAmq1Ih3A9Wm5HIv")

        // define symbols
        mPointSymbol = SimpleMarkerSymbol(SimpleMarkerSymbol.Style.SQUARE, -0x10000, 20F)
        mLineSymbol = SimpleLineSymbol(SimpleLineSymbol.Style.SOLID, -0x7800, 4F)
        mFillSymbol = SimpleFillSymbol(SimpleFillSymbol.Style.CROSS, 0x40FFA9A9, mLineSymbol)

        // inflate map view from layout
        mMapView = findViewById(R.id.mapView)
        // create a map with the Basemap Style topographic
        map = ArcGISMap(BasemapStyle.ARCGIS_IMAGERY)

        // create the service feature table
        val serviceFeatureTable = ServiceFeatureTable("https://services7.arcgis.com/PgeIirzmbuRv6GNJ/arcgis/rest/services/FarmerBoundry4/FeatureServer/0")

        // create the feature layer using the service feature table
        val featureLayer = FeatureLayer(serviceFeatureTable)
        map.operationalLayers.add(featureLayer)

        // set the map to be displayed in the layout's MapView
        mMapView.map = map
        // set the viewpoint, Viewpoint(latitude, longitude, scale)
        mMapView.setViewpoint(Viewpoint(14.0566957,77.3593152, 5000.0))

        mGraphicsOverlay = GraphicsOverlay()
        mMapView.getGraphicsOverlays().add(mGraphicsOverlay)

        // create a new sketch editor and add it to the map view
        mSketchEditor = SketchEditor()
        mMapView.setSketchEditor(mSketchEditor)

        // get buttons from layouts
        mPointButton = findViewById(R.id.pointButton)
        mMultiPointButton = findViewById(R.id.pointsButton)
        mPolylineButton = findViewById(R.id.polylineButton)
        mPolygonButton = findViewById(R.id.polygonButton)
        mFreehandLineButton = findViewById(R.id.freehandLineButton)
        mFreehandPolygonButton = findViewById(R.id.freehandPolygonButton)

        //===========SURESH======================
        mPointButton.visibility = View.GONE
        //===========SURESH======================

        // add click listeners
        mPointButton.setOnClickListener(View.OnClickListener { view: View? -> createModePoint() })
        mMultiPointButton.setOnClickListener(View.OnClickListener { view: View? -> createModeMultipoint() })
        mPolylineButton.setOnClickListener(View.OnClickListener { view: View? -> createModePolyline() })
        mPolygonButton.setOnClickListener(View.OnClickListener { view: View? -> createModePolygon() })
        mFreehandLineButton.setOnClickListener(View.OnClickListener { view: View? -> createModeFreehandLine() })
        mFreehandPolygonButton.setOnClickListener(View.OnClickListener { view: View? -> createModeFreehandPolygon() })

        //============SURESH=============

        //skechMap()

       //=========SURESH=================

    }


    private fun skechMap() {
        val polygonPoints = PointCollection(SpatialReferences.getWgs84()).apply {
            // Point(latitude, longitude)
            add(Point(77.35797770502093, 15.48432))
            add(Point(75.4931, 15.55508))
            add(Point(75.4641,15.55238))
            add(Point(75.4827, 15.55383))
        }


        val blueOutlineSymbol = SimpleLineSymbol(SimpleLineSymbol.Style.SOLID, -0xff9c01, 2f)

        // create a polygon geometry from the point collection
        val polygon = Polygon(polygonPoints)

        val polygonFillSymbol =
            SimpleFillSymbol(SimpleFillSymbol.Style.SOLID, -0x7f00a8cd, blueOutlineSymbol)

        // create a polygon graphic from the polygon geometry and symbol
        val graphic = Graphic(polygon, polygonFillSymbol)

        // Log.e(TAG, "Json : "+graphic.geometry.toJson())
        // Log.e(TAG, "dimension : "+graphic.geometry.dimension)

        // add the graphic to the graphics overlay
        mGraphicsOverlay!!.graphics.add(graphic)

    }

    /**
     * When the point button is clicked, reset other buttons, show the point button as selected, and start point
     * drawing mode.
     */
    private fun createModePoint() {
        resetButtons()
        mPointButton.isSelected = true
        mSketchEditor!!.start(SketchCreationMode.POINT)
    }

    /**
     * When the multipoint button is clicked, reset other buttons, show the multipoint button as selected, and start
     * multipoint drawing mode.
     */
    private fun createModeMultipoint() {
        resetButtons()
        mMultiPointButton.isSelected = true
        mSketchEditor!!.start(SketchCreationMode.MULTIPOINT)
    }

    /**
     * When the polyline button is clicked, reset other buttons, show the polyline button as selected, and start
     * polyline drawing mode.
     */
    private fun createModePolyline() {
        resetButtons()
        mPolylineButton.isSelected = true
        mSketchEditor!!.start(SketchCreationMode.POLYLINE)
    }

    /**
     * When the polygon button is clicked, reset other buttons, show the polygon button as selected, and start polygon
     * drawing mode.
     */
    private fun createModePolygon() {
        resetButtons()
        mPolygonButton.isSelected = true
        mSketchEditor!!.start(SketchCreationMode.POLYGON)
    }

    /**
     * When the freehand line button is clicked, reset other buttons, show the freehand line button as selected, and
     * start freehand line drawing mode.
     */
    private fun createModeFreehandLine() {
        resetButtons()
        mFreehandLineButton.isSelected = true
        mSketchEditor!!.start(SketchCreationMode.FREEHAND_LINE)
    }

    /**
     * When the freehand polygon button is clicked, reset other buttons, show the freehand polygon button as selected,
     * and enable freehand polygon drawing mode.
     */
    private fun createModeFreehandPolygon() {
        resetButtons()
        mFreehandPolygonButton.isSelected = true
        mSketchEditor!!.start(SketchCreationMode.FREEHAND_POLYGON)
    }

    /**
     * When the undo button is clicked, undo the last event on the SketchEditor.
     */
    private fun undo() {
        if (mSketchEditor!!.canUndo()) {
            mSketchEditor!!.undo()
        }
    }

    /**
     * When the redo button is clicked, redo the last undone event on the SketchEditor.
     */
    private fun redo() {
        if (mSketchEditor!!.canRedo()) {
            mSketchEditor!!.redo()
        }
    }

    /**
     * When the stop button is clicked, check that sketch is valid. If so, get the geometry from the sketch, set its
     * symbol and add it to the graphics overlay.
     */
    private fun stop() {
        if (!mSketchEditor!!.isSketchValid) {
            reportNotValid()
            mSketchEditor!!.stop()
            resetButtons()
            return
        }

        // get the geometry from sketch editor
        val sketchGeometry = mSketchEditor!!.geometry
        mSketchEditor!!.stop()
        resetButtons()
        if (sketchGeometry != null) {

            // create a graphic from the sketch editor geometry
            val graphic = Graphic(sketchGeometry)

            // assign a symbol based on geometry type
            if (graphic.geometry.geometryType == GeometryType.POLYGON) {
                Log.e(TAG, "mFillSymbol Json : "+mFillSymbol?.toJson())

                graphic.symbol = mFillSymbol

                Log.e(TAG, "Edited Json : "+graphic.geometry.toJson())
                Log.e(TAG, "spatialReference : "+graphic.geometry.spatialReference.toJson())
                Log.e(TAG, "dimension : "+graphic.geometry.dimension)
                //Log.e(TAG, "mFillSymbol json : "+mFillSymbol?.toJson())


            } else if (graphic.geometry.geometryType == GeometryType.POLYLINE) {
                graphic.symbol = mLineSymbol
            } else if (graphic.geometry.geometryType == GeometryType.POINT ||
                graphic.geometry.geometryType == GeometryType.MULTIPOINT
            ) {
                graphic.symbol = mPointSymbol
            }

            // add the graphic to the graphics overlay
            mGraphicsOverlay!!.graphics.add(graphic)
        }
    }

    /**
     * Called if sketch is invalid. Reports to user why the sketch was invalid.
     */
    private fun reportNotValid() {
        val validIf: String
        validIf = if (mSketchEditor!!.sketchCreationMode == SketchCreationMode.POINT) {
            "Point only valid if it contains an x & y coordinate."
        } else if (mSketchEditor!!.sketchCreationMode == SketchCreationMode.MULTIPOINT) {
            "Multipoint only valid if it contains at least one vertex."
        } else if (mSketchEditor!!.sketchCreationMode == SketchCreationMode.POLYLINE
            || mSketchEditor!!.sketchCreationMode == SketchCreationMode.FREEHAND_LINE
        ) {
            "Polyline only valid if it contains at least one part of 2 or more vertices."
        } else if (mSketchEditor!!.sketchCreationMode == SketchCreationMode.POLYGON
            || mSketchEditor!!.sketchCreationMode == SketchCreationMode.FREEHAND_POLYGON
        ) {
            "Polygon only valid if it contains at least one part of 3 or more vertices which form a closed ring."
        } else {
            "No sketch creation mode selected."
        }
        val report = "Sketch geometry invalid:\n$validIf"
        val reportSnackbar =
            Snackbar.make(findViewById(R.id.toolbarInclude), report, Snackbar.LENGTH_INDEFINITE)
        reportSnackbar.setAction(
            "Dismiss"
        ) { view: View? -> reportSnackbar.dismiss() }
        val snackbarTextView =
            reportSnackbar.view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
        snackbarTextView.isSingleLine = false
        reportSnackbar.show()
        Log.e(TAG, report)
    }

    /**
     * De-selects all buttons.
     */
    private fun resetButtons() {
        mPointButton.isSelected = false
        mMultiPointButton.isSelected = false
        mPolylineButton.isSelected = false
        mPolygonButton.isSelected = false
        mFreehandLineButton.isSelected = false
        mFreehandPolygonButton.isSelected = false
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.undo_redo_stop_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.undo) {
            undo()
        } else if (id == R.id.redo) {
            redo()
        } else if (id == R.id.stop) {
            stop()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onPause() {
        mMapView.pause()
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
        mMapView.resume()
    }

    override fun onDestroy() {
        super.onDestroy()
        mMapView.dispose()
    }

}
