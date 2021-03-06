package com.alphawallet.app.entity.tokenscript;

import com.alphawallet.app.BuildConfig;
import com.alphawallet.app.repository.TokenRepository;
import com.alphawallet.app.util.BalanceUtils;
import com.alphawallet.app.web3j.FunctionEncoder;
import com.alphawallet.app.web3j.FunctionReturnDecoder;
import com.alphawallet.app.web3j.TypeReference;
import com.alphawallet.app.web3j.datatypes.Function;
import com.alphawallet.token.entity.AttributeInterface;
import com.alphawallet.token.entity.AttributeType;
import com.alphawallet.token.entity.ContractAddress;
import com.alphawallet.token.entity.FunctionDefinition;
import com.alphawallet.token.entity.MethodArg;
import com.alphawallet.token.entity.TokenScriptResult;
import com.alphawallet.token.entity.TokenscriptContext;
import com.alphawallet.token.entity.TokenscriptElement;
import com.alphawallet.token.entity.TransactionResult;
import com.alphawallet.token.tools.TokenDefinition;

import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Int;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Uint;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.*;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthCall;
import org.web3j.utils.Numeric;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.Observable;

import static org.web3j.protocol.core.methods.request.Transaction.createEthCallTransaction;

/**
 * Created by James on 13/06/2019.
 * Stormbird in Sydney
 */
public abstract class TokenscriptFunction
{
    public static final String TOKENSCRIPT_CONVERSION_ERROR = "<error>";

    public Function generateTransactionFunction(String walletAddress, BigInteger tokenId, TokenDefinition definition, FunctionDefinition function, AttributeInterface attrIf)
    {
        //pre-parse tokenId.
        if (tokenId.bitCount() > 256) tokenId = tokenId.or(BigInteger.ONE.shiftLeft(256).subtract(BigInteger.ONE)); //truncate tokenId too large
        if (walletAddress == null) walletAddress = ZERO_ADDRESS;

        List<Type> params = new ArrayList<Type>();
        List<TypeReference<?>> returnTypes = new ArrayList<TypeReference<?>>();
        for (MethodArg arg : function.parameters)
        {
            resolveReference(walletAddress, arg, tokenId, definition, attrIf);
            switch (arg.parameterType)
            {

                case "int":
                    params.add(new Int(new BigInteger(arg.element.value)));
                    break;
                case "int8":
                    params.add(new Int8(new BigInteger(arg.element.value)));
                    break;
                case "int16":
                    params.add(new Int16(new BigInteger(arg.element.value)));
                    break;
                case "int24":
                    params.add(new Int24(new BigInteger(arg.element.value)));
                    break;
                case "int32":
                    params.add(new Int32(new BigInteger(arg.element.value)));
                    break;
                case "int40":
                    params.add(new Int40(new BigInteger(arg.element.value)));
                    break;
                case "int48":
                    params.add(new Int48(new BigInteger(arg.element.value)));
                    break;
                case "int56":
                    params.add(new Int56(new BigInteger(arg.element.value)));
                    break;
                case "int64":
                    params.add(new Int64(new BigInteger(arg.element.value)));
                    break;
                case "int72":
                    params.add(new Int72(new BigInteger(arg.element.value)));
                    break;
                case "int80":
                    params.add(new Int80(new BigInteger(arg.element.value)));
                    break;
                case "int88":
                    params.add(new Int88(new BigInteger(arg.element.value)));
                    break;
                case "int96":
                    params.add(new Int96(new BigInteger(arg.element.value)));
                    break;
                case "int104":
                    params.add(new Int104(new BigInteger(arg.element.value)));
                    break;
                case "int112":
                    params.add(new Int112(new BigInteger(arg.element.value)));
                    break;
                case "int120":
                    params.add(new Int120(new BigInteger(arg.element.value)));
                    break;
                case "int128":
                    params.add(new Int128(new BigInteger(arg.element.value)));
                    break;
                case "int136":
                    params.add(new Int136(new BigInteger(arg.element.value)));
                    break;
                case "int144":
                    params.add(new Int144(new BigInteger(arg.element.value)));
                    break;
                case "int152":
                    params.add(new Int152(new BigInteger(arg.element.value)));
                    break;
                case "int160":
                    params.add(new Int160(new BigInteger(arg.element.value)));
                    break;
                case "int168":
                    params.add(new Int168(new BigInteger(arg.element.value)));
                    break;
                case "int176":
                    params.add(new Int176(new BigInteger(arg.element.value)));
                    break;
                case "int184":
                    params.add(new Int184(new BigInteger(arg.element.value)));
                    break;
                case "int192":
                    params.add(new Int192(new BigInteger(arg.element.value)));
                    break;
                case "int200":
                    params.add(new Int200(new BigInteger(arg.element.value)));
                    break;
                case "int208":
                    params.add(new Int208(new BigInteger(arg.element.value)));
                    break;
                case "int216":
                    params.add(new Int216(new BigInteger(arg.element.value)));
                    break;
                case "int224":
                    params.add(new Int224(new BigInteger(arg.element.value)));
                    break;
                case "int232":
                    params.add(new Int232(new BigInteger(arg.element.value)));
                    break;
                case "int240":
                    params.add(new Int240(new BigInteger(arg.element.value)));
                    break;
                case "int248":
                    params.add(new Int248(new BigInteger(arg.element.value)));
                    break;
                case "int256":
                    params.add(new Int256(new BigInteger(arg.element.value)));
                    break;
                case "uint":
                    params.add(new Uint(new BigInteger(arg.element.value)));
                    break;
                case "uint8":
                    params.add(new Uint8(new BigInteger(arg.element.value)));
                    break;
                case "uint16":
                    params.add(new Uint16(new BigInteger(arg.element.value)));
                    break;
                case "uint24":
                    params.add(new Uint24(new BigInteger(arg.element.value)));
                    break;
                case "uint32":
                    params.add(new Uint32(new BigInteger(arg.element.value)));
                    break;
                case "uint40":
                    params.add(new Uint40(new BigInteger(arg.element.value)));
                    break;
                case "uint48":
                    params.add(new Uint48(new BigInteger(arg.element.value)));
                    break;
                case "uint56":
                    params.add(new Uint56(new BigInteger(arg.element.value)));
                    break;
                case "uint64":
                    params.add(new Uint64(new BigInteger(arg.element.value)));
                    break;
                case "uint72":
                    params.add(new Uint72(new BigInteger(arg.element.value)));
                    break;
                case "uint80":
                    params.add(new Uint80(new BigInteger(arg.element.value)));
                    break;
                case "uint88":
                    params.add(new Uint88(new BigInteger(arg.element.value)));
                    break;
                case "uint96":
                    params.add(new Uint96(new BigInteger(arg.element.value)));
                    break;
                case "uint104":
                    params.add(new Uint104(new BigInteger(arg.element.value)));
                    break;
                case "uint112":
                    params.add(new Uint112(new BigInteger(arg.element.value)));
                    break;
                case "uint120":
                    params.add(new Uint120(new BigInteger(arg.element.value)));
                    break;
                case "uint128":
                    params.add(new Uint128(new BigInteger(arg.element.value)));
                    break;
                case "uint136":
                    params.add(new Uint136(new BigInteger(arg.element.value)));
                    break;
                case "uint144":
                    params.add(new Uint144(new BigInteger(arg.element.value)));
                    break;
                case "uint152":
                    params.add(new Uint152(new BigInteger(arg.element.value)));
                    break;
                case "uint160":
                    params.add(new Uint160(new BigInteger(arg.element.value)));
                    break;
                case "uint168":
                    params.add(new Uint168(new BigInteger(arg.element.value)));
                    break;
                case "uint176":
                    params.add(new Uint176(new BigInteger(arg.element.value)));
                    break;
                case "uint184":
                    params.add(new Uint184(new BigInteger(arg.element.value)));
                    break;
                case "uint192":
                    params.add(new Uint192(new BigInteger(arg.element.value)));
                    break;
                case "uint200":
                    params.add(new Uint200(new BigInteger(arg.element.value)));
                    break;
                case "uint208":
                    params.add(new Uint208(new BigInteger(arg.element.value)));
                    break;
                case "uint216":
                    params.add(new Uint216(new BigInteger(arg.element.value)));
                    break;
                case "uint224":
                    params.add(new Uint224(new BigInteger(arg.element.value)));
                    break;
                case "uint232":
                    params.add(new Uint232(new BigInteger(arg.element.value)));
                    break;
                case "uint240":
                    params.add(new Uint240(new BigInteger(arg.element.value)));
                    break;
                case "uint248":
                    params.add(new Uint248(new BigInteger(arg.element.value)));
                    break;
                case "uint256":
                    switch (arg.element.ref)
                    {
                        case "tokenId":
                            params.add(new Uint256(tokenId));
                            break;
                        case "value":
                        default:
                            params.add(new Uint256(new BigInteger(arg.element.value)));
                            break;
                    }
                    break;
                case "address":
                    switch (arg.element.ref)
                    {
                        case "ownerAddress":
                            params.add(new Address(walletAddress));
                            break;
                        case "value":
                        default:
                            params.add(new Address(arg.element.value));
                            break;
                    }
                    break;
                case "string":
                    params.add(new Utf8String(arg.element.value));
                    break;
                case "bytes":
                    params.add(new Bytes32(Numeric.hexStringToByteArray(arg.element.value)));
                    break;
                case "bytes1":
                    params.add(new Bytes1(Numeric.hexStringToByteArray(arg.element.value)));
                    break;
                case "bytes2":
                    params.add(new Bytes2(Numeric.hexStringToByteArray(arg.element.value)));
                    break;
                case "bytes3":
                    params.add(new Bytes3(Numeric.hexStringToByteArray(arg.element.value)));
                    break;
                case "bytes4":
                    params.add(new Bytes4(Numeric.hexStringToByteArray(arg.element.value)));
                    break;
                case "bytes5":
                    params.add(new Bytes5(Numeric.hexStringToByteArray(arg.element.value)));
                    break;
                case "bytes6":
                    params.add(new Bytes6(Numeric.hexStringToByteArray(arg.element.value)));
                    break;
                case "bytes7":
                    params.add(new Bytes7(Numeric.hexStringToByteArray(arg.element.value)));
                    break;
                case "bytes8":
                    params.add(new Bytes8(Numeric.hexStringToByteArray(arg.element.value)));
                    break;
                case "bytes9":
                    params.add(new Bytes9(Numeric.hexStringToByteArray(arg.element.value)));
                    break;
                case "bytes10":
                    params.add(new Bytes10(Numeric.hexStringToByteArray(arg.element.value)));
                    break;
                case "bytes11":
                    params.add(new Bytes11(Numeric.hexStringToByteArray(arg.element.value)));
                    break;
                case "bytes12":
                    params.add(new Bytes12(Numeric.hexStringToByteArray(arg.element.value)));
                    break;
                case "bytes13":
                    params.add(new Bytes13(Numeric.hexStringToByteArray(arg.element.value)));
                    break;
                case "bytes14":
                    params.add(new Bytes14(Numeric.hexStringToByteArray(arg.element.value)));
                    break;
                case "bytes15":
                    params.add(new Bytes15(Numeric.hexStringToByteArray(arg.element.value)));
                    break;
                case "bytes16":
                    params.add(new Bytes16(Numeric.hexStringToByteArray(arg.element.value)));
                    break;
                case "bytes17":
                    params.add(new Bytes17(Numeric.hexStringToByteArray(arg.element.value)));
                    break;
                case "bytes18":
                    params.add(new Bytes18(Numeric.hexStringToByteArray(arg.element.value)));
                    break;
                case "bytes19":
                    params.add(new Bytes19(Numeric.hexStringToByteArray(arg.element.value)));
                    break;
                case "bytes20":
                    params.add(new Bytes20(Numeric.hexStringToByteArray(arg.element.value)));
                    break;
                case "bytes21":
                    params.add(new Bytes21(Numeric.hexStringToByteArray(arg.element.value)));
                    break;
                case "bytes22":
                    params.add(new Bytes22(Numeric.hexStringToByteArray(arg.element.value)));
                    break;
                case "bytes23":
                    params.add(new Bytes23(Numeric.hexStringToByteArray(arg.element.value)));
                    break;
                case "bytes24":
                    params.add(new Bytes24(Numeric.hexStringToByteArray(arg.element.value)));
                    break;
                case "bytes25":
                    params.add(new Bytes25(Numeric.hexStringToByteArray(arg.element.value)));
                    break;
                case "bytes26":
                    params.add(new Bytes26(Numeric.hexStringToByteArray(arg.element.value)));
                    break;
                case "bytes27":
                    params.add(new Bytes27(Numeric.hexStringToByteArray(arg.element.value)));
                    break;
                case "bytes28":
                    params.add(new Bytes28(Numeric.hexStringToByteArray(arg.element.value)));
                    break;
                case "bytes29":
                    params.add(new Bytes29(Numeric.hexStringToByteArray(arg.element.value)));
                    break;
                case "bytes30":
                    params.add(new Bytes30(Numeric.hexStringToByteArray(arg.element.value)));
                    break;
                case "bytes31":
                    params.add(new Bytes31(Numeric.hexStringToByteArray(arg.element.value)));
                    break;
                case "bytes32":
                    params.add(new Bytes32(Numeric.hexStringToByteArray(arg.element.value)));
                    break;
                default:
                    System.out.println("NOT IMPLEMENTED: " + arg.parameterType);
                    break;
            }
        }
        switch (function.syntax)
        {
            //not used as of now
            case Boolean:
            case Integer:
            case NumericString:
                returnTypes.add(new TypeReference<Uint256>()
                {
                });
                break;
            case IA5String:
            case DirectoryString:
                returnTypes.add(new TypeReference<Utf8String>()
                {
                });
                break;
        }

        return new Function(function.method,
                            params, returnTypes);
    }

    public static byte[] convertArgToBytes(String inputValue)
    {
        byte[] argBytes = new byte[1];
        try
        {
            String hexValue = inputValue;
            if (!Numeric.containsHexPrefix(inputValue))
            {
                BigInteger value;
                try
                {
                    value = new BigInteger(inputValue);
                }
                catch (NumberFormatException e)
                {
                    e.printStackTrace();
                    value = new BigInteger(inputValue, 16);
                }

                hexValue = Numeric.toHexStringNoPrefix(value.toByteArray());
                //fix sign condition
                if (hexValue.length() > 64 && hexValue.startsWith("00"))
                {
                    hexValue = hexValue.substring(2);
                }
            }

            argBytes = Numeric.hexStringToByteArray(hexValue);
        }
        catch (Exception e)
        {
            //no action
        }

        return argBytes;
    }

    private void handleTransactionResult(TransactionResult result, Function function, String responseValue, FunctionDefinition fd, long lastTransactionTime)
    {
        try
        {
            //try to interpret the value
            List<Type> response = FunctionReturnDecoder.decode(responseValue, function.getOutputParameters());
            if (response.size() > 0)
            {
                result.resultTime = lastTransactionTime;
                Type val = response.get(0);
                switch (fd.syntax)
                {
                    case Boolean:
                        BigDecimal value = new BigDecimal(((Uint256) val).getValue());
                        result.result = value.equals(BigDecimal.ZERO) ? "FALSE" : "TRUE";
                        break;
                    case Integer:
                    case NumericString:
                        result.result = new BigDecimal(((Uint256) val).getValue()).toString();
                        break;
                    case IA5String:
                    case DirectoryString:
                        result.result = (String) response.get(0).getValue();
                        if (responseValue.length() > 2 && result.result.length() == 0)
                        {
                            result.result = checkBytesString(responseValue);
                        }
                        break;
                }
            }
            else
            {
                result.resultTime = 0;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private String checkBytesString(String responseValue) throws Exception
    {
        String name = "";
        if (responseValue.length() > 0)
        {
            //try raw bytes
            byte[] data = Numeric.hexStringToByteArray(responseValue);
            //check leading bytes for non-zero
            if (data[0] != 0)
            {
                //truncate zeros
                int index = data.length - 1;
                while (data[index] == 0 && index > 0)
                    index--;
                if (index != (data.length - 1))
                {
                    data = Arrays.copyOfRange(data, 0, index + 1);
                }
                name = new String(data, "UTF-8");
            }
        }

        return name;
    }

    public TokenScriptResult.Attribute parseFunctionResult(TransactionResult transactionResult, AttributeType attr)
    {
        String res = attr.getSyntaxVal(transactionResult.result);
        BigInteger val = transactionResult.tokenId; //?

        if (attr.syntax == TokenDefinition.Syntax.NumericString)
        {
            if (transactionResult.result == null)
            {
                res = "0";
            }
            else if (transactionResult.result.startsWith("0x"))
            {
                res = res.substring(2);
            }
            try
            {
                val = new BigInteger(res, 16);
            }
            catch (NumberFormatException e)
            {
                val = BigInteger.ZERO;
            }
        }
        return new TokenScriptResult.Attribute(attr.id, attr.name, val, res);
    }

    public static final String ZERO_ADDRESS = "0x0000000000000000000000000000000000000000";

    /**
     * Haven't pre-cached this value yet, so need to fetch it before we can proceed
     * @param override
     * @param attr
     * @param tokenId
     * @param definition
     * @return
     */
    public Observable<TransactionResult> fetchResultFromEthereum(String walletAddress, ContractAddress override, AttributeType attr,
                                                                 BigInteger tokenId, TokenDefinition definition, AttributeInterface attrIf, long lastTransactionTime)
    {
        return Observable.fromCallable(() -> {
            ContractAddress useAddress;
            if (override == null) // contract not specified - is not holder contract
            {
                //determine address using definition context
                useAddress = new ContractAddress(attr.function, definition.context.cAddr.chainId, definition.context.cAddr.address);
            }
            else
            {
                useAddress = override;
            }
            TransactionResult transactionResult = new TransactionResult(useAddress.chainId, useAddress.address, tokenId, attr);
            Function transaction = generateTransactionFunction(walletAddress, tokenId, definition, attr.function, attrIf);

            //now push the transaction
            String result = callSmartContractFunction(TokenRepository.getWeb3jService(useAddress.chainId), transaction, useAddress.address, ZERO_ADDRESS);

            handleTransactionResult(transactionResult, transaction, result, attr.function, lastTransactionTime);
            return transactionResult;
        });
    }

    private String callSmartContractFunction(Web3j web3j,
                                             Function function, String contractAddress, String walletAddr)
    {
        String encodedFunction = FunctionEncoder.encode(function);

        try
        {
            org.web3j.protocol.core.methods.request.Transaction transaction
                    = createEthCallTransaction(walletAddr, contractAddress, encodedFunction);
            EthCall response = web3j.ethCall(transaction, DefaultBlockParameterName.LATEST).send();

            return response.getValue();
        }
        catch (IOException e)
        {
            //Connection error. Use cached value
            return null;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    private void resolveReference(String walletAddress, MethodArg arg, BigInteger tokenId, TokenDefinition definition, AttributeInterface attrIf)
    {
        if (definition != null && definition.attributeTypes.containsKey(arg.element.ref))
        {
            arg.element.value = fetchAttrResult(walletAddress, arg.element.ref, tokenId, null, definition, attrIf, 0).blockingSingle().text;
        }
    }

    /**
     * Fetches a TokenScript attribute
     *
     * May either return a static attribute sourced from the Token ID or a dynamic one from a contract function
     *
     * If a dynamic function, then test to see if we can source the value from the result cache -
     * Test to see if the contract has seen any transactions AFTER the result was cached, if so then invalidate the result and re-fetch
     *
     * TODO: there is currently an optimisation issue with this. If the contract being called to resolve the attribute is not the token
     *       contract which the script refers to then the result is not cached. This can be seen eg with 'ENABLE' functions which permit
     *       the script contract to manipulate the tokens which the 'ENABLE' function is being called on.
     *       It may not be possible to always safely cache these values; even with an event handler we have to interpret those events and invalidate
     *       any cached results. However if we're tracking the referenced contract as a token then it should be safe
     *
     * @param walletAddress
     * @param attribute
     * @param tokenId
     * @param cAddr
     * @param td
     * @param attrIf
     * @param transactionUpdate
     * @return
     */
    public Observable<TokenScriptResult.Attribute> fetchAttrResult(String walletAddress, String attribute, BigInteger tokenId, ContractAddress cAddr, TokenDefinition td, AttributeInterface attrIf, long transactionUpdate)
    {
        AttributeType attr = td.attributeTypes.get(attribute);
        if (attr == null || isAttrIncomplete(attr)) return Observable.fromCallable(() -> new TokenScriptResult.Attribute("bd", "bd", BigInteger.ZERO, ""));
        if (attr.function == null)  // static attribute from tokenId (eg city mapping from tokenId)
        {
            return staticAttribute(attr, tokenId);
        }
        else
        {
            ContractAddress useAddress;
            if (cAddr == null) useAddress = new ContractAddress(attr.function);
            else useAddress = new ContractAddress(attr.function, cAddr.chainId, cAddr.address);
            TransactionResult cachedResult = attrIf.getFunctionResult(useAddress, attr, tokenId); //Needs to allow for multiple tokenIds
            if (cAddr != null && !useAddress.address.equalsIgnoreCase(cAddr.address)) transactionUpdate = 0; //If calling a function which isn't the main tokenscript function retrieve from contract call not cache
            if (!attr.isVolatile() && (attrIf.resolveOptimisedAttr(useAddress, attr, cachedResult) || !cachedResult.needsUpdating(transactionUpdate))) //can we use wallet's known data or cached value?
            {
                return resultFromDatabase(cachedResult, attr);
            }
            else  //if cached value is invalid or if value is dynamic
            {
                return fetchResultFromEthereum(walletAddress, useAddress, attr, tokenId, td, attrIf, transactionUpdate)       // Fetch function result from blockchain
                        .map(result -> restoreFromDBIfRequired(result, cachedResult))  // If network unavailable restore value from cache
                        .map(attrIf::storeAuxData)                                     // store new data
                        .map(result -> parseFunctionResult(result, attr));    // write returned data into attribute
            }
        }
    }

    private boolean isAttrIncomplete(AttributeType attr)
    {
        if (attr.function == null) return false;

        for (MethodArg arg : attr.function.parameters)
        {
            int index = arg.getTokenIndex();
            if (arg.isTokenId() && index >= 0 && (arg.element.value == null || arg.element.value.length() == 0))
            {
                return true;
            }
        }

        return false;
    }

    public Observable<TokenScriptResult.Attribute> resolveAttributes(String walletAddress, BigInteger tokenId, AttributeInterface attrIf, ContractAddress cAddr, TokenDefinition td, long transactionUpdate)
    {
        td.context = new TokenscriptContext();
        td.context.cAddr = cAddr;
        td.context.attrInterface = attrIf;

        return Observable.fromIterable(new ArrayList<>(td.attributeTypes.values()))
                .flatMap(attr -> fetchAttrResult(walletAddress, attr.id, tokenId, cAddr, td, attrIf, transactionUpdate));
    }

    private Observable<TokenScriptResult.Attribute> staticAttribute(AttributeType attr, BigInteger tokenId)
    {
        return Observable.fromCallable(() -> {
            try
            {
                BigInteger val = tokenId.and(attr.bitmask).shiftRight(attr.bitshift);
                if (BuildConfig.DEBUG) System.out.println("ATTR: " + attr.name + " : " + attr.id + " : " + attr.getSyntaxVal(attr.toString(val)));
                return new TokenScriptResult.Attribute(attr.id, attr.name, val, attr.getSyntaxVal(attr.toString(val)));
            }
            catch (Exception e)
            {
                return new TokenScriptResult.Attribute(attr.id, attr.name, tokenId, "unsupported encoding");
            }
        });
    }

    private Observable<TokenScriptResult.Attribute> resultFromDatabase(TransactionResult transactionResult, AttributeType attr)
    {
        return Observable.fromCallable(() -> parseFunctionResult(transactionResult, attr));
    }

    /**
     * Restore result from Database if required (eg connection failure), and if there was a database value to restore
     * @param result return from calling contract function
     * @param cachedResult previous return value, restored from database
     * @return
     */
    private TransactionResult restoreFromDBIfRequired(TransactionResult result, TransactionResult cachedResult)
    {
        if (result.resultTime == 0 && cachedResult != null && result.result == null)
        {
            result.result = cachedResult.result;
            result.resultTime = cachedResult.resultTime;
        }

        return result;
    }

    public String convertInputValue(AttributeType attr, TokenscriptElement e, String valueFromInput)
    {
        String convertedValue = "";
        try
        {
            byte[] inputBytes;
            switch (attr.as)
            {
                //UTF8, Unsigned, Signed, Mapping, Boolean, UnsignedInput, TokenId
                case Unsigned:
                case Signed:
                case UnsignedInput:
                    inputBytes = TokenscriptFunction.convertArgToBytes(valueFromInput);
                    BigInteger unsignedValue = new BigInteger(inputBytes);
                    convertedValue = unsignedValue.toString();
                    e.value = unsignedValue.toString();
                    break;
                case UTF8:
                    e.value = valueFromInput;
                    convertedValue = valueFromInput;
                    break;
                case Bytes:
                    //apply bitmask to user entry and shift it because bytes is the other way round
                    inputBytes = TokenscriptFunction.convertArgToBytes(valueFromInput);
                    if (inputBytes.length <= 32)
                    {
                        BigInteger val = new BigInteger(1, inputBytes).and(attr.bitmask).shiftRight(attr.bitshift);
                        e.value = val.toString(16);
                    }
                    else
                    {
                        e.value = com.alphawallet.token.tools.Numeric.toHexString(inputBytes);
                    }
                    convertedValue = e.value;
                    break;
                case e18:
                    e.value = BalanceUtils.EthToWei(valueFromInput);
                    convertedValue = e.value;
                    break;
                case e8:
                    e.value = BalanceUtils.UnitToEMultiplier(valueFromInput, new BigDecimal("100000000"));
                    convertedValue = e.value;
                    break;
                case e4:
                    e.value = BalanceUtils.UnitToEMultiplier(valueFromInput, new BigDecimal("1000"));
                    convertedValue = e.value;
                    break;
                case e2:
                    e.value = BalanceUtils.UnitToEMultiplier(valueFromInput, new BigDecimal("100"));
                    convertedValue = e.value;
                    break;
                case Mapping:
                    //makes no sense as input
                    break;
                case Boolean:
                    //attempt to decode
                    if (valueFromInput.equalsIgnoreCase("true") || valueFromInput.equals("1"))
                    {
                        e.value = "TRUE";
                    }
                    else
                    {
                        e.value = "FALSE";
                    }
                    convertedValue = e.value;
                    break;
                case TokenId:
                    break;
            }
        }
        catch (Exception excp)
        {
            excp.printStackTrace();
            convertedValue = TOKENSCRIPT_CONVERSION_ERROR + excp.getMessage();
        }

        return convertedValue;
    }
}
