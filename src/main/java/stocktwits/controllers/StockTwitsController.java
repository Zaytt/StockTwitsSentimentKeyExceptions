package stocktwits.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientException;
import stocktwits.exception.SymbolNotFoundException;
import stocktwits.exception.WrongAPIKeyException;
import stocktwits.model.TickerStreamRoot;
import stocktwits.model.analyze.CompareSentiment;
import stocktwits.model.analyze.TickerSentiment;
import stocktwits.services.StockTwitsService;
import stocktwits.services.UserService;

import java.util.ArrayList;

@RestController
@RequestMapping("stocktwits")
public class StockTwitsController {

    @Autowired
    StockTwitsService stockTwitsService;
    @Autowired
    UserService userService;



    @RequestMapping("/stream/{ticker:.+}")
    public TickerStreamRoot getTickerStream(@PathVariable(value="ticker")String ticker,
                                            @RequestParam(value="key")String key)
                                            throws WrongAPIKeyException, SymbolNotFoundException, RestClientException {
        //Validate the user API key
        if(!userService.validateUserAPIKey(key)) throw new WrongAPIKeyException();

        return stockTwitsService.getStreamTicker(ticker);
    }

    @RequestMapping("/sentiment/{ticker:.+}")
    public TickerSentiment getTickerSentiment(@PathVariable(value="ticker")String ticker,
                                              @RequestParam(value="key")String key) throws WrongAPIKeyException {
        //Validate the user API key
        if(!userService.validateUserAPIKey(key)) throw new WrongAPIKeyException();
        return stockTwitsService.getSentimentByTicker(ticker);
    }

    @RequestMapping("/sentiment/{ticker:.+}/{datetime}")
    public TickerSentiment getTickerSentiment(@PathVariable(value="ticker")String ticker,
                                              @PathVariable(value="datetime")String datetime,
                                              @RequestParam(value="key")String key) throws WrongAPIKeyException {
        return stockTwitsService.getSentimentByPK(ticker, datetime);
    }

    @RequestMapping("/sentiment/compare")
    public CompareSentiment compareSentiment(@RequestParam(value="ticker1")String ticker1,
                                             @RequestParam(value="ticker2")String ticker2,
                                             @RequestParam(value="key")String key)throws WrongAPIKeyException {
        //Validate the user API key
        if(!userService.validateUserAPIKey(key)) throw new WrongAPIKeyException();
        return stockTwitsService.compareSentiment(ticker1, ticker2);
    }

    //----------TICKER SENTIMENT CRUD---------------

    // Create Ticker Sentiment
    @RequestMapping(method = RequestMethod.POST, value = "/sentiment")
    public TickerSentiment insertTickerSentiment(@RequestBody TickerSentiment tickerSentiment,
                                                 @RequestParam(value="key")String key) throws WrongAPIKeyException {
        //Validate the user API key
        if(!userService.validateUserAPIKey(key)) throw new WrongAPIKeyException();
        return stockTwitsService.insertTickerSentiment(tickerSentiment);
    }

    // Read Ticker Sentiment History
    @RequestMapping(method= RequestMethod.GET, value = "/getsentiment/{ticker:.+}/{datetime}")
    public TickerSentiment getTickerSentimentDB(@PathVariable(value="ticker")String ticker,
                                                @PathVariable(value="ticker")String datetime,
                                                @RequestParam(value="key")String key) throws WrongAPIKeyException {
        //Validate the user API key
        if(!userService.validateUserAPIKey(key)) throw new WrongAPIKeyException();
        return stockTwitsService.getSentimentByPK(ticker, datetime);
    }

    // Read Ticker Sentiment History
    @RequestMapping(method= RequestMethod.GET, value = "/getsentiment/{ticker:.+}")
    public ArrayList<TickerSentiment> getTickerSentimentHistory(@PathVariable(value="ticker")String ticker,
                                                                @RequestParam(value="key")String key) throws WrongAPIKeyException {
        //Validate the user API key
        if(!userService.validateUserAPIKey(key)) throw new WrongAPIKeyException();
        return stockTwitsService.getTickerSentimentHistory(ticker);
    }

    // Update Ticker Sentiment
    @RequestMapping(method = RequestMethod.PATCH, value = "/")
    public TickerSentiment updateByPK(@RequestBody TickerSentiment tickerSentiment,
                                      @RequestParam(value="key")String key) throws WrongAPIKeyException {
        //Validate the user API key
        if(!userService.validateUserAPIKey(key)) throw new WrongAPIKeyException();
        return stockTwitsService.updateByPK(tickerSentiment);
    }

    // Delete Ticker Sentiment
    @RequestMapping(method = RequestMethod.DELETE, value = "/{ticker:.+}/{datetime}")
    public TickerSentiment deleteByPK(@PathVariable(value="ticker")String ticker,
                                      @PathVariable(value="datetime")String datetime,
                                      @RequestParam(value="key")String key) throws WrongAPIKeyException {
        //Validate the user API key
        if(!userService.validateUserAPIKey(key)) throw new WrongAPIKeyException();
        return stockTwitsService.deleteByPK(ticker, datetime);
    }

}
