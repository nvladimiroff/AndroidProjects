package vladimiroff.csu.foodrss;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class DownloadRssFeed extends AsyncTask<String, Void, ArrayList<SingleItem>> {
    ShowRecipe callerContext;
    String url;
    String title;
    ProgressDialog dialog = null;

    public DownloadRssFeed(Context callerContext) {
        this.callerContext = (ShowRecipe) callerContext;
        dialog = new ProgressDialog(callerContext);
    }

    @Override
    protected void onPreExecute() {
        this.dialog.setMessage("Loading...");
        this.dialog.setCancelable(false);
        this.dialog.show();
    }

    @Override
    protected ArrayList<SingleItem> doInBackground(String... params) {
        ArrayList<SingleItem> recipeList = new ArrayList<SingleItem>();
        url = params[0];
        title = params[1];

        this.dialog.setMessage("Loading " + title);

        try {
            URL url = new URL(this.url);
            URLConnection connection;
            connection = url.openConnection();

            HttpURLConnection httpConnection = (HttpURLConnection) connection;
            int responseCode = httpConnection.getResponseCode();

            if(responseCode == HttpURLConnection.HTTP_OK) {
                InputStream in = httpConnection.getInputStream();
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder db = dbf.newDocumentBuilder();
                Document dom = db.parse(in);
                Element treeElements = dom.getDocumentElement();

                recipeList.clear();
                NodeList itemNodes = treeElements.getElementsByTagName("item");
                if((itemNodes != null) && (itemNodes.getLength() > 0)) {
                    for(int i=0; i< itemNodes.getLength(); i++) {
                        recipeList.add(dissectItemNode(itemNodes, i));
                        //Log.e("Got node", dissectItemNode(itemNodes, i).toString());
                    }
                }
            }
            httpConnection.disconnect();
        } catch(Exception e) {
            Log.e("Error>> ", e.getMessage());
        }

        return recipeList;
    }

    @Override
    protected void onPostExecute(ArrayList<SingleItem> result) {
        super.onPostExecute(result);
        callerContext.recipeList = result;

        int layoutID = R.layout.item_list;
        ArrayAdapter<SingleItem> adapterRecipe =
                new ArrayAdapter<SingleItem>(callerContext, layoutID, result);
        callerContext.listView.setAdapter(adapterRecipe);

        dialog.dismiss();
    }

    public SingleItem dissectItemNode(NodeList nodeList, int i) {
        try {
            Element entry = (Element) nodeList.item(i);
            Element title = (Element) entry.getElementsByTagName("title").item(0);
            Element description = (Element) entry.getElementsByTagName("description").item(0);
            Element pubDate = (Element) entry.getElementsByTagName("pubDate").item(0);
            Element link = (Element) entry.getElementsByTagName("link").item(0);

            String titleValue = title.getFirstChild().getNodeValue();
            String desciptionValue = description.getFirstChild().getNodeValue();
            String dateValue = pubDate.getFirstChild().getNodeValue();
            String linkValue = link.getFirstChild().getNodeValue();

            SingleItem singleItem = new SingleItem(dateValue, titleValue, desciptionValue, linkValue);
            return singleItem;
        } catch (DOMException e) {
            return new SingleItem("", "Error", e.getMessage(), null);
        }
    }
}
