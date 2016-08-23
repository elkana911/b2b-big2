package com.big.web.b2b_big2.flight.filter;

import java.io.IOException;

import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.index.TermDocs;
import org.apache.lucene.search.DocIdSet;
import org.apache.lucene.util.OpenBitSet;

// please read https://forum.hibernate.org/viewtopic.php?f=9&t=993596&view=previous
//https://docs.jboss.org/hibernate/search/3.1/reference/en/html/ch05s03.html
public class AirportAsiaFilter extends org.apache.lucene.search.Filter{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public DocIdSet getDocIdSet(IndexReader reader) throws IOException {
	      OpenBitSet  bitSet = new OpenBitSet ( reader.maxDoc() );
	        TermDocs termDocs = reader.termDocs( new Term( "country_id", "62" ) );
	        while ( termDocs.next() ) {
	            bitSet.set( termDocs.doc() );
	        }
	        return bitSet;
	}

	
}
