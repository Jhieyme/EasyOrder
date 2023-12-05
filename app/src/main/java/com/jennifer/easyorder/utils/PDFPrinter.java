package com.jennifer.easyorder.utils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.pdf.PdfDocument;
import android.os.Build;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.ParcelFileDescriptor;
import android.print.PageRange;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintDocumentInfo;
import android.print.PrintJob;
import android.print.PrintManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jennifer.easyorder.R;
import com.jennifer.easyorder.data.RestaurantInterface;
import com.jennifer.easyorder.data.RetrofitHelper;
import com.jennifer.easyorder.model.CustomerWorker;
import com.jennifer.easyorder.model.DetailOrder;
import com.jennifer.easyorder.model.Order;
import com.jennifer.easyorder.model.Voucher;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PDFPrinter {

    private static final String TAG = "PDFPrinter";

    private PrintJob printJob;


    public void printToCocina(Order order, View content, Context context, List<DetailOrder> listDetail) {
        PdfDocument document = createPdfDocument();
        drawContentOnCanvas(document, content, order, context, listDetail);
        showPrintOptions(document, context);
    }

    public void printToVoucher(Order order, View content, Context context, List<DetailOrder> listDetail, Voucher voucherResponse) {
        PdfDocument document = createPdfDocument();
        drawContentOnCanvasVoucher(document, content, order, context, listDetail, voucherResponse);

    }

    private PdfDocument createPdfDocument() {
        return new PdfDocument();
    }


    private void drawContentOnCanvas(PdfDocument document, View content, Order order, Context context, List<DetailOrder> listDetail) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            content.getContext().getDisplay().getRealMetrics(displayMetrics);
        } else {
            ((WindowManager) context.getApplicationContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(displayMetrics);
        }

        int viewWidth = displayMetrics.widthPixels;
        int viewHeight = displayMetrics.heightPixels;

        // Ajustar el ancho de la vista para que coincida con el ancho del papel del PDF
        int pdfWidth = viewWidth;
        int pdfHeight = viewHeight;

        // Obtener el contenedor LinearLayout
        LinearLayout lnAdd = content.findViewById(R.id.lnCocinaProduct);
        LayoutInflater inflater = LayoutInflater.from(context.getApplicationContext());


        int totalQnt = 0;
        for (DetailOrder detailOrder : listDetail) {
            View detailView = inflater.inflate(R.layout.print_product_row, null);

            TextView tvName = detailView.findViewById(R.id.tvNameRow);
            TextView tvQntRow = detailView.findViewById(R.id.tvQntRow);
            tvName.setText(detailOrder.getIdProductoNavigation().getNombre());
            tvQntRow.setText(String.valueOf("x" + detailOrder.getCantidad()));

            totalQnt += detailOrder.getCantidad();
            lnAdd.addView(detailView);
        }

        String nroMesa = String.valueOf(order.getIdMesaNavigation().getNroMesa());
        String nroComanda = String.valueOf(order.getIdComanda());
        String totalQntView = String.valueOf(totalQnt);

        TextView nroMesaView = content.findViewById(R.id.tvNumMesa);
        TextView nroComandaView = content.findViewById(R.id.tvNroComanda);
        TextView nroFecha = content.findViewById(R.id.tvFecha);
        TextView totalQntTv = content.findViewById(R.id.tvTotalQnt);
        nroMesaView.setText("Nro Mesa: " + nroMesa);
        nroComandaView.setText("N°COMANDA: " + nroComanda);


        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        String dateTime = order.getFechaHora();
        Date fecha = null;
        try {
            fecha = inputFormat.parse(dateTime);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        SimpleDateFormat outputFormat = new SimpleDateFormat("E, dd HH:mm a", new Locale("es", "PE"));
        String dateTimeFormat = outputFormat.format(fecha);
        nroFecha.setText(dateTimeFormat);

        totalQntTv.setText(totalQntView);


        // Medir y disponer la vista después de agregar elementos dinámicos
        int measuredWidth = View.MeasureSpec.makeMeasureSpec(pdfWidth, View.MeasureSpec.EXACTLY);
        int measuredHeight = View.MeasureSpec.makeMeasureSpec(pdfHeight, View.MeasureSpec.EXACTLY);
        content.measure(measuredWidth, measuredHeight);

        // Asegurarse de que la vista tenga los márgenes y paddings necesarios
        content.layout(0, 0, content.getMeasuredWidth(), content.getMeasuredHeight());

        // Crear el lienzo y dibujar la vista
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(pdfWidth, pdfHeight, 1).create();
        PdfDocument.Page page = document.startPage(pageInfo);
        Canvas canvas = page.getCanvas();

        // Dibujar la vista en el lienzo
        content.draw(canvas);
        lnAdd.removeAllViewsInLayout();
        document.finishPage(page);


    }

    private void drawContentOnCanvasVoucher(PdfDocument document, View content, Order order, Context context, List<DetailOrder> listDetail, Voucher voucherResponse) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            content.getContext().getDisplay().getRealMetrics(displayMetrics);
        } else {
            ((WindowManager) context.getApplicationContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(displayMetrics);
        }

        int viewWidth = displayMetrics.widthPixels;
        int viewHeight = displayMetrics.heightPixels;

        // Ajustar el ancho de la vista para que coincida con el ancho del papel del PDF
        int pdfWidth = viewWidth;
        int pdfHeight = viewHeight;

        // Obtener el contenedor LinearLayout
        LinearLayout lnAdd = content.findViewById(R.id.lnVoucherProduct);
        LayoutInflater inflater = LayoutInflater.from(context.getApplicationContext());
        double total = 0;
        for (DetailOrder detailOrder : listDetail) {
            View detailView = inflater.inflate(R.layout.print_productvoucher_row, null);
            TextView tvName = detailView.findViewById(R.id.tvNameRow);
            TextView tvQntRow = detailView.findViewById(R.id.tvQntRow);
            TextView tvPrecio = detailView.findViewById(R.id.tvPrecio);
            TextView tvImporte = detailView.findViewById(R.id.tvImporte);
            tvPrecio.setText(String.valueOf(detailOrder.getIdProductoNavigation().getPrecio()));
            tvName.setText(detailOrder.getIdProductoNavigation().getNombre());
            tvQntRow.setText(String.valueOf("x" + detailOrder.getCantidad()));
            tvImporte.setText("S/" + detailOrder.getImporte());

            total += detailOrder.getImporte();
            lnAdd.addView(detailView);
        }


        TextView nroVoucher = content.findViewById(R.id.tvNroVoucher);
        nroVoucher.setText(String.valueOf("N° Boleta: " + voucherResponse.getIdBoleta()));


        TextView tvFecha = content.findViewById(R.id.tvFecha);
        Date fechaActual = new Date();
        SimpleDateFormat fechaFormato = new SimpleDateFormat("dd/MM/yyyy");
        String parseFecha = fechaFormato.format(fechaActual);
        tvFecha.setText(parseFecha);


        TextView tvHora = content.findViewById(R.id.tvHora);
        Date horaActual = new Date();
        SimpleDateFormat horaFormato = new SimpleDateFormat("HH:mm", new Locale("es", "PE"));
        String parseHora = horaFormato.format(horaActual);
        tvHora.setText(parseHora);


        TextView tvTotal = content.findViewById(R.id.tvTotal);
        tvTotal.setText("TOTAL: S/" + total);


        int idBoleta = voucherResponse.getIdBoleta();
        RestaurantInterface apiService = RetrofitHelper.getInstance().create(RestaurantInterface.class);
        Call<CustomerWorker> test = apiService.getByIdBoleta(idBoleta);

        test.enqueue(new Callback<CustomerWorker>() {
            @Override
            public void onResponse(Call<CustomerWorker> call, Response<CustomerWorker> response) {
                CustomerWorker item = response.body();


                TextView tvNameCliente = content.findViewById(R.id.tvCliente);
                tvNameCliente.setText(item.getCliente().getNombres() + " " + item.getCliente().getApellidos());

                TextView tvDni = content.findViewById(R.id.tvDNI);
                tvDni.setText(item.getCliente().getDni());


                TextView tvCajero = content.findViewById(R.id.tvCajero);
                tvCajero.setText(item.getPersonal().getNombre() + " " + item.getPersonal().getApellidos());

                // Medir y disponer la vista después de agregar elementos dinámicos
                int measuredWidth = View.MeasureSpec.makeMeasureSpec(pdfWidth, View.MeasureSpec.EXACTLY);
                int measuredHeight = View.MeasureSpec.makeMeasureSpec(pdfHeight, View.MeasureSpec.EXACTLY);
                content.measure(measuredWidth, measuredHeight);

                // Asegurarse de que la vista tenga los márgenes y paddings necesarios
                content.layout(0, 0, content.getMeasuredWidth(), content.getMeasuredHeight());

                // Crear el lienzo y dibujar la vista
                PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(pdfWidth, pdfHeight, 1).create();
                PdfDocument.Page page = document.startPage(pageInfo);
                Canvas canvas = page.getCanvas();

                // Dibujar la vista en el lienzo
                content.draw(canvas);

                document.finishPage(page);
                lnAdd.removeAllViews();

                showPrintOptions(document, context);


            }

            @Override
            public void onFailure(Call<CustomerWorker> call, Throwable t) {

            }
        });

    }

    private void showPrintOptions(PdfDocument document, Context context) {
        // Show print options
        PrintManager printManager = (PrintManager) context.getSystemService(Context.PRINT_SERVICE);
        String jobName = "Print Job";

        if (printManager != null) {

            PrintAttributes.Builder printAttributes = new PrintAttributes.Builder().setMediaSize(PrintAttributes.MediaSize.JPN_YOU4);


            // Crear un objeto PrintDocumentAdapter
            PrintDocumentAdapter printAdapter = new PrintDocumentAdapter() {
                @Override
                public void onWrite(PageRange[] pages, ParcelFileDescriptor destination, CancellationSignal cancellationSignal, WriteResultCallback callback) {
                    try {
                        FileOutputStream out = new FileOutputStream(destination.getFileDescriptor());
                        document.writeTo(out);
                        callback.onWriteFinished(new PageRange[]{PageRange.ALL_PAGES});
                        out.close();
                    } catch (IOException e) {
                        Log.e(TAG, "Error writing PDF document", e);
                    }
                }

                @Override
                public void onLayout(PrintAttributes oldAttributes, PrintAttributes newAttributes, CancellationSignal cancellationSignal, LayoutResultCallback callback, Bundle extras) {
                    if (cancellationSignal.isCanceled()) {
                        callback.onLayoutCancelled();
                        return;
                    }

                    // Implementar el código necesario para el diseño (si es necesario)
                    // ...

                    PrintDocumentInfo pdi = new PrintDocumentInfo.Builder("document.pdf").setContentType(PrintDocumentInfo.CONTENT_TYPE_DOCUMENT).build();
                    callback.onLayoutFinished(pdi, true);
                }
            };


            printManager.print(jobName, printAdapter, printAttributes.build());


        }
    }


}


