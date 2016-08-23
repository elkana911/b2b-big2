package com.big.web.ws.quote.bean;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
/*
-<StockQuotes>
	-<Stock>
		<Symbol>AAPL</Symbol>
		<Last>112.98</Last>
		<Date>1/23/2015</Date>
		<Time>4:00pm</Time>
		<Change>+0.58</Change>
		<Open>112.32</Open>
		<High>113.75</High>
		<Low>111.53</Low>
		<Volume>46464828</Volume>
		<MktCap>662.6B</MktCap>
		<PreviousClose>112.40</PreviousClose>
		<PercentageChange>+0.52%</PercentageChange>
		<AnnRange>70.5071 - 119.75</AnnRange>
		<Earns>6.45</Earns>
		<P-E>17.43</P-E>
		<Name>Apple Inc.</Name>
	</Stock>
</StockQuotes>
*/

@XmlRootElement(name="StockQuotes")
public class SQuotes {
	private SQuote stock;

	public SQuote getStock() {
		return stock;
	}

	@XmlElement(name="Stock")
	public void setStock(SQuote stock) {
		this.stock = stock;
	}

	@Override
	public String toString() {
		return "SQuotes [stock=" + stock + "]";
	}
	
	
}
