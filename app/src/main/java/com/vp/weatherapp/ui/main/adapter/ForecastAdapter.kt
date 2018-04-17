package com.vp.weatherapp.ui.main.adapter


//class ForecastAdapter(ctx: Context, val weekForecast: ForecastList)
//    : RecyclerView.Adapter<ForecastAdapter.ForecastViewHolder>() {
//
//    companion object {
//        val VIEW_TYPE_HEADER = 0
//        val VIEW_TYPE_DAY = 1
//        var fmtDeg: String? = null
//        var fmtCityCountry: String? = null
//    }
//
//    private val items = ArrayList<ListItem>()
//
//    init {
//        fmtDeg = ctx.getString(R.string.fmt_degrees_celsius)
//        fmtCityCountry = ctx.getString(R.string.fmt_city_country)
//        items.add(HeaderItem(weekForecast.city, weekForecast.country, weekForecast.dailyForecast[0]))
//        (1..weekForecast.dailyForecast.size-1).mapTo(items) {
//            DayItem(weekForecast.dailyForecast[it])
//        }
//    }
//
//    override fun getItemCount(): Int = weekForecast.dailyForecast.size
//
//    override fun getItemViewType(position: Int) = when(items[position]) {
//        is HeaderItem -> VIEW_TYPE_HEADER
//        is DayItem -> VIEW_TYPE_DAY
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastViewHolder {
//        val view = when(viewType) {
//            VIEW_TYPE_HEADER -> parent.context.layoutInflater.inflate(R.layout.list_item_header, parent, false)
//            else -> parent.context.layoutInflater.inflate(R.layout.list_item_day, parent, false)
//        }
//
//        return ForecastViewHolder(view)
//    }
//
//    override fun onBindViewHolder(holder: ForecastViewHolder, position: Int) {
//        val item = items[position]
//        holder.bind(item)
//    }
//
//
//    class ForecastViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
//
//        fun bind(item: ListItem) {
//            when(item) {
//                is HeaderItem -> {
//                    itemView.tvLocationName.text = fmtCityCountry?.format(item.city, item.country)
//                    itemView.tvDate.text = item.forecast.date
//                    itemView.tvTemp.text = fmtDeg?.format(item.forecast.avg)
//                }
//                is DayItem -> {
//                    val forecast = item.forecast
//                    itemView.tvDayOfWeek.text = forecast.date
//                    itemView.tvDescription.text = forecast.description
//                    itemView.tvMaxTemp.text = fmtDeg?.format(forecast.high)
//                    itemView.tvAvgTemp.text = fmtDeg?.format(forecast.avg)
//                    itemView.tvMinTemp.text = fmtDeg?.format(forecast.low)
//                }
//            }
//        }
//    }
//
//    sealed class ListItem() {
//        class HeaderItem(val city: String, val country: String, val forecast: Forecast): ListItem()
//        class DayItem(val forecast: Forecast): ListItem()
//    }
//
//}