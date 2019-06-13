package com.template.webserver;
import com.template.beans.*;
import com.template.flows.BankDetailsUpdateFlow;
import com.template.flows.Initiator;
import com.template.flows.LenderBorrowerFlow;
import com.template.flows.LendingProposalFlow;
import com.template.states.*;
import net.corda.core.contracts.StateAndRef;
import net.corda.core.contracts.UniqueIdentifier;
import net.corda.core.identity.CordaX500Name;
import net.corda.core.identity.Party;
import net.corda.core.messaging.CordaRPCOps;
import net.corda.core.node.services.Vault;
import net.corda.core.node.services.vault.QueryCriteria;
import net.corda.core.transactions.SignedTransaction;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

/**
 * Define your API endpoints here.
 */
@RestController
@RequestMapping("/") // The paths for HTTP requests are relative to this base path.
public class Controller {
    private final CordaRPCOps proxy;
    private final CordaX500Name myLegalName;
    private final static Logger logger = LoggerFactory.getLogger(Controller.class);



    public Controller(NodeRPCConnection rpc) {
        this.proxy = rpc.proxy;
        this.myLegalName = rpc.proxy.nodeInfo().getLegalIdentities().get(0).getName();
    }

    @GetMapping(value = "/templateendpoint", produces = "text/plain")
    private String templateendpoint() {
        return "Define an endpoint here.";
    }

    @PostMapping("/userReg")
    public ResponseEntity<?> userRegistration(@RequestBody UserBean userBean) throws InterruptedException, ExecutionException
    {
        Party myParty = proxy.wellKnownPartyFromX500Name(myLegalName);
        CordaX500Name otherNode = CordaX500Name.parse("O=PartyB,L=New York,C=US");
        Party otherParty = proxy.wellKnownPartyFromX500Name(otherNode);
        logger.info("OtherParty: "+otherParty);
        if(otherParty == null){
            return new ResponseEntity<String>("Other party Shouldn't be null", HttpStatus.BAD_REQUEST);
        }
        if(userBean.getEmail() == null)
        {
            return new ResponseEntity<String>("Email shouldn't be null",HttpStatus.BAD_REQUEST);
        }
        if(userBean.getPassword() == null)
        {
            return new ResponseEntity<String>("Password shouldn't be null",HttpStatus.BAD_REQUEST);
        }
        if(userBean.getFirstName() == null)
        {
            return new ResponseEntity<String>("FirstName shouldn't be null",HttpStatus.BAD_REQUEST);
        }

        UserState userState = new UserState(userBean.getFirstName(),userBean.getMiddleName(),userBean.getLastName(),userBean.getEmail(),userBean.getPassword(),userBean.getMobile(),new UniqueIdentifier(), myParty,otherParty);
        logger.info("UserState before committing" +userState);
        try{
            final SignedTransaction signedTx = proxy
                    .startFlowDynamic(Initiator.class, userState)
                    .getReturnValue()
                    .get();
            UserState responseState = (UserState)signedTx.getCoreTransaction().getOutputStates().get(0);
            logger.info("userState after signed Transaction :"+responseState);
            UserDetails userDetails = new UserDetails(responseState.getEmail(),responseState.getUserId());
            return new ResponseEntity<UserDetails>(userDetails,HttpStatus.CREATED);
         }catch(Throwable ex) {
            return new ResponseEntity<Error>(HttpStatus.BAD_REQUEST);
        }
    }

//    @PostMapping("/updateUser")
//    public ResponseEntity<?> updateUserDetails(@RequestBody UserBankDetails bankDetails){
//
//        Party myParty = proxy.wellKnownPartyFromX500Name(myLegalName);
//        CordaX500Name otherPartyNode = CordaX500Name.parse("O=PartyB,L=New York,C=US");
//        Party otherParty = proxy.wellKnownPartyFromX500Name(otherPartyNode);
//        logger.info("OtherParty: "+otherParty);
//        if(otherParty == null){
//            return new ResponseEntity<String>("Other party Shouldn't be null", HttpStatus.BAD_REQUEST);
//        }
//
//        if(bankDetails.getAccountNumber()==null)
//        {
//            return new ResponseEntity<String>("Account number shouldn't be null",HttpStatus.BAD_REQUEST);
//        }
//        if(bankDetails.getBankName()==null)
//        {
//            return new ResponseEntity<String>("Bank name shouldn't be null",HttpStatus.BAD_REQUEST);
//        }
//        if(bankDetails.getBranchName()==null)
//        {
//            return new ResponseEntity<String>("Branch shouldn't be null",HttpStatus.BAD_REQUEST);
//        }
//        if(bankDetails.getIfscCode()==null)
//        {
//            return new ResponseEntity<String>("IFSC code shouldn't be null",HttpStatus.BAD_REQUEST);
//        }
//        if(bankDetails.getPanNumber()==null)
//        {
//            return new ResponseEntity<String>("PAN number shouldn't be null",HttpStatus.BAD_REQUEST);
//        }
//        if(bankDetails.getUserId()==null)
//        {
//            return new ResponseEntity<String>("UserID shouldn't be null",HttpStatus.BAD_REQUEST);
//        }

//        List<UUID> listOfLinearIDs = Arrays.asList(bankDetails.getUserId().getId());
//        if(listOfLinearIDs == null){
//            return new ResponseEntity<String>("UserID is incorrect",HttpStatus.BAD_REQUEST);
//        }
//        QueryCriteria queryCriteria = new QueryCriteria.LinearStateQueryCriteria(null, listOfLinearIDs);
//        Vault.Page results = proxy.vaultQueryByCriteria(queryCriteria,UserState.class);
//        StateAndRef inputStateAndRef = (StateAndRef) results.getStates().get(0);
//        UserState inputState = (UserState) inputStateAndRef.getState().getData();

//        inputState.setAccountNumber(bankDetails.getAccountNumber());
//        inputState.setPanNumber(bankDetails.getPanNumber());
//        inputState.setIfscCode(bankDetails.getIfscCode());
//        inputState.setBankName(bankDetails.getBankName());
//        inputState.setBranchName(bankDetails.getBranchName());
//        logger.info("UserState before Committing: "+inputState);
//        if(inputState.getOtherParty() == null){
//            return new ResponseEntity<String>("Other party Shouldn't be null", HttpStatus.BAD_REQUEST);
//        }
//        UserBankDetailsState userBankDetailsState = new UserBankDetailsState(bankDetails.getUserId(),bankDetails.getPanNumber(),bankDetails.getAccountNumber(),bankDetails.getIfscCode(),bankDetails.getBankName(),bankDetails.getBranchName(),bankDetails.getAadharCard(),bankDetails.getAddress().getDoor_number()
//                ,bankDetails.getAddress().getStreet_name(),bankDetails.getAddress().getArea(),bankDetails.getAddress().getCity(),bankDetails.getAddress().getState(),bankDetails.getAddress().getPin(),bankDetails.getAddress().getLandmark(),myParty,otherParty);
//        logger.info("UserState before Committing: "+userBankDetailsState);
//        try{
//            final SignedTransaction signedTx = proxy
//                    .startFlowDynamic(BankDetailsUpdateFlow.class, userBankDetailsState)
//                    .getReturnValue()
//                    .get();
//
//                logger.info("Signed Tx: "+signedTx.getTx());
//                UserBankDetailsState responseState = (UserBankDetailsState) signedTx.getCoreTransaction().getOutputStates().get(0);
//                logger.info("UserState after committing the transaction: "+responseState);
//                return new ResponseEntity<String>("Bank details added Successfully",HttpStatus.CREATED);
//
//            }catch(Throwable ex){
//            return new ResponseEntity<Error>(HttpStatus.BAD_REQUEST);
//        }
//    }

    @PostMapping("/userUpdate")
    public ResponseEntity<?> userUpdate(@RequestBody UserBankDetails userBankDetails) {

        List<UUID> listOfLinearIDs = Arrays.asList(userBankDetails.getUserId().getId());
        if (listOfLinearIDs == null) {
            return new ResponseEntity<String>("UserID is incorrect", HttpStatus.BAD_REQUEST);
        }

        QueryCriteria queryCriteria = new QueryCriteria.LinearStateQueryCriteria(null, listOfLinearIDs);
        Vault.Page results = proxy.vaultQueryByCriteria(queryCriteria, UserState.class);
        StateAndRef inputStateAndRef = (StateAndRef) results.getStates().get(0);
        UserState inputState = (UserState) inputStateAndRef.getState().getData();

        logger.info("Input State: " + inputState);
        UserBankDetailsState userBankDetailsState = new UserBankDetailsState(
                inputState.getFirstName(),
                inputState.getMiddleName(),
                inputState.getLastName(),
                inputState.getEmail(),
                inputState.getPassword(),
                inputState.getMobile(),
                inputState.getUserId(),
                userBankDetails.getPanNumber(),
                userBankDetails.getAccountNumber(),
                userBankDetails.getIfscCode(),
                userBankDetails.getBankName(),
                userBankDetails.getBranchName(),
                userBankDetails.getAadharCard(),
                userBankDetails.getAddress().getDoor_number(),
                userBankDetails.getAddress().getStreet_name(),
                userBankDetails.getAddress().getArea(),
                userBankDetails.getAddress().getCity(),
                userBankDetails.getAddress().getState(),
                userBankDetails.getAddress().getPin(),
                userBankDetails.getAddress().getLandmark(),
                inputState.getMyParty(),
                inputState.getOtherParty()
        );
        System.out.println("UserDetails new :" + userBankDetailsState);
        try {
            final SignedTransaction signedTx = proxy
                    .startFlowDynamic(BankDetailsUpdateFlow.class, userBankDetailsState, inputStateAndRef)
                    .getReturnValue()
                    .get();
            logger.info("Transaction state after committing:  "+signedTx.getCoreTransaction().getOutputStates().get(0));
            UserBankDetailsState responseState = (UserBankDetailsState) signedTx.getCoreTransaction().getOutputStates().get(0);
            UserDetails userDetails = new UserDetails(responseState.getEmail(),responseState.getUserId());
            return new ResponseEntity<UserDetails>(userDetails,HttpStatus.CREATED);
        }catch (Throwable ex) {
            return new ResponseEntity<String>("Not updated", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/lendingProposal")
    public ResponseEntity<?> lendingProposal(@RequestBody LendingProposalBean lendingProposalBean){

        Party myParty = proxy.wellKnownPartyFromX500Name(myLegalName);
        CordaX500Name otherNode = CordaX500Name.parse("O=PartyB,L=New York,C=US");
        Party otherParty = proxy.wellKnownPartyFromX500Name(otherNode);
        logger.info("OtherParty: "+otherParty);
        if(otherParty == null){
            return new ResponseEntity<String>("Other party Shouldn't be null", HttpStatus.BAD_REQUEST);
        }
        if(lendingProposalBean.getAmount() <= 0){
            return new ResponseEntity<String>("Amount should be greater than Zero",HttpStatus.BAD_REQUEST);
        }
        if(lendingProposalBean.getDurationInMonths() < 0){
            return new ResponseEntity<String>("Duration should be greater than Zero",HttpStatus.BAD_REQUEST);
        }
        if(lendingProposalBean.getInterest_rate() < 0){
            return new ResponseEntity<String>("Interest rate should be greater than Zero",HttpStatus.BAD_REQUEST);
        }
        if(lendingProposalBean.getLender_id() == null) {
            return new ResponseEntity<String>("LenderId shouldn't be null", HttpStatus.BAD_REQUEST);
        }

       // UniqueIdentifier loanNumber = new UniqueIdentifier();
        double platform_fee = lendingProposalBean.getAmount()*0.01;
        double p = lendingProposalBean.getAmount();
        double r = (lendingProposalBean.getInterest_rate())/100;
        int t = lendingProposalBean.getDurationInMonths()/12;
        double n = 12;

        double interest = (p*(Math.pow((1+(r/n)),(n*t))))-p;
        LocalDateTime contract_initiation_time = LocalDateTime.now();

        LendingProposalState lendingProposalState = new LendingProposalState(
                 lendingProposalBean.getUser_credit_type()
                ,lendingProposalBean.getInterest_rate()
                ,lendingProposalBean.getAmount()
                ,lendingProposalBean.getDurationInMonths()
                ,lendingProposalBean.getLoan_category()
                ,lendingProposalBean.getWriteup()
                ,lendingProposalBean.getLender_id()
                ,new UniqueIdentifier()
                ,contract_initiation_time
                ,interest
                ,platform_fee
                ,myParty
                ,otherParty);
        logger.info("Lending proposal before committing: "+lendingProposalState);
        try{
            final SignedTransaction signedTx = proxy
                    .startFlowDynamic(LendingProposalFlow.class, lendingProposalState)
                    .getReturnValue()
                    .get();
            logger.info("Signed Tx: "+signedTx.getTx());
            LendingProposalState responseState = (LendingProposalState) signedTx.getCoreTransaction().getOutputStates().get(0);
            logger.info("Lending proposal after committing the transaction: "+responseState);
            LendingProposalDetails lendingProposalDetails = new LendingProposalDetails(responseState);
            logger.info("details"+lendingProposalDetails);
            return new ResponseEntity<LendingProposalDetails>(lendingProposalDetails,HttpStatus.CREATED);

        } catch (Throwable ex){
            return new ResponseEntity<String>("Error in committing lending proposal",HttpStatus.BAD_REQUEST);
        }

    }

    @PostMapping("/lenderBorrowerAcceptance")
    public ResponseEntity<?> lenderBorrowerAcceptance(@RequestBody LenderBorrowerBean lenderBorrowerBean){

        List<UUID> listOfLinearIDs = Arrays.asList(lenderBorrowerBean.getLoanNumber().getId());
        if(listOfLinearIDs == null){
            return new ResponseEntity<String>("UserID is incorrect",HttpStatus.BAD_REQUEST);
        }

        QueryCriteria queryCriteria = new QueryCriteria.LinearStateQueryCriteria(null, listOfLinearIDs);
        Vault.Page results = proxy.vaultQueryByCriteria(queryCriteria,LendingProposalState.class);
        StateAndRef inputStateAndRef = (StateAndRef) results.getStates().get(0);
        LendingProposalState inputState = (LendingProposalState) inputStateAndRef.getState().getData();

        logger.info("Input State: "+inputState);
        lenderBorrowerBean.setContract_enforcement_time(LocalDateTime.now());
        LenderBorrowerState lenderBorrowerState = new LenderBorrowerState(
                inputState.getUser_credit_type(),
                inputState.getInterest_rate(),
                inputState.getAmount(),
                inputState.getDurationInMonths(),
                inputState.getLoan_category(),
                inputState.getWriteup(),
                inputState.getContract_initiation_time(),
                inputState.getInterest(),
                inputState.getPlatform_fee()
                ,lenderBorrowerBean.getLoanNumber(),
                lenderBorrowerBean.getBorrower_id(),
                lenderBorrowerBean.getLender_id(),
                lenderBorrowerBean.getContract_enforcement_time(),
                inputState.getMyParty(),inputState.getOtherParty());
        logger.info("LenderBorrower state before commiting: " +lenderBorrowerState);
        try{
            final SignedTransaction signedTx = proxy
                    .startFlowDynamic(LenderBorrowerFlow.class, inputStateAndRef,lenderBorrowerState)
                    .getReturnValue()
                    .get();
            logger.info("Signed Transaction after committing: "+signedTx.getCoreTransaction().getOutputStates().get(0));
            LenderBorrowerState responseState = (LenderBorrowerState)signedTx.getCoreTransaction().getOutputStates().get(0);
            LendingProposalDetails lendingProposalDetails = new LendingProposalDetails(responseState);
            lendingProposalDetails.setContract_enforcement_time(responseState.getContract_enforcement_time());
            lendingProposalDetails.setBorrower_id(responseState.getBorrower_id());
            //return ResponseEntity.ok(lendingProposalDetails);
            return new ResponseEntity<LendingProposalDetails>(lendingProposalDetails,HttpStatus.CREATED);
        }catch (Throwable ex) {
            return new ResponseEntity<String>("Not accepted", HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<?> monthlyPayment(@RequestBody PaymentDetails paymentDetails) throws InterruptedException, ExecutionException{

        if(paymentDetails.getContract_enforcement_time().plusMonths(1) == LocalDateTime.now()) {
            List<UUID> listOfLinearIDs = Arrays.asList(paymentDetails.getLoanNumber().getId());
            if (listOfLinearIDs == null) {
                return new ResponseEntity<String>("UserID is incorrect", HttpStatus.BAD_REQUEST);
            }

            QueryCriteria queryCriteria = new QueryCriteria.LinearStateQueryCriteria(null, listOfLinearIDs);
            Vault.Page results = proxy.vaultQueryByCriteria(queryCriteria, LenderBorrowerState.class);
            StateAndRef inputStateAndRef = (StateAndRef) results.getStates().get(0);
            LenderBorrowerState inputState = (LenderBorrowerState) inputStateAndRef.getState().getData();

            logger.info("Input State: " + inputState);
            LocalDateTime payment_to_be_done = inputState.getContract_enforcement_time().plusMonths(1);
            double total_amount_to_be_paid = inputState.getAmount() + inputState.getInterest();
            double emi = (total_amount_to_be_paid) / inputState.getDurationInMonths();
            double default_fine = 0;
            double next_payment_amount = emi + default_fine;
            LocalDateTime next_payment_date = payment_to_be_done.plusMonths(1);
            LocalDateTime last_payment_date = inputState.getContract_enforcement_time().plusMonths(inputState.getDurationInMonths());

            PaymentDetailsState paymentDetailsState = new PaymentDetailsState();
            paymentDetailsState.setLoanNumber(paymentDetails.getLoanNumber());
            paymentDetailsState.setAmount_paid(paymentDetails.getAmount_paid());
            paymentDetailsState.setPayment_done_on(LocalDateTime.now());
            paymentDetailsState.setTotal_amount_to_be_paid(total_amount_to_be_paid);
            paymentDetailsState.setDefault_fine(default_fine);
            paymentDetailsState.setNext_payment_date(next_payment_date);
            paymentDetailsState.setNext_payment_amount(next_payment_amount);
            paymentDetailsState.setLast_payment_date(last_payment_date);
            paymentDetailsState.setContract_enforcement_time(paymentDetails.getContract_enforcement_time());
            paymentDetailsState.setMyParty(inputState.getMyParty());
            paymentDetailsState.setOtherParty(inputState.getOtherParty());
        }
        else {
            List<UUID> listOfLinearIDs = Arrays.asList(paymentDetails.getLoanNumber().getId());
            if (listOfLinearIDs == null) {
                return new ResponseEntity<String>("UserID is incorrect", HttpStatus.BAD_REQUEST);
            }

            QueryCriteria queryCriteria = new QueryCriteria.LinearStateQueryCriteria(null, listOfLinearIDs);
            Vault.Page results = proxy.vaultQueryByCriteria(queryCriteria, PaymentDetailsState.class);
            StateAndRef inputStateAndRef = (StateAndRef) results.getStates().get(0);
            PaymentDetailsState inputState = (PaymentDetailsState) inputStateAndRef.getState().getData();

            double default_fine = 0;
            double next_payment_amount;
            LocalDateTime
            if(inputState.getNext_payment_date() == LocalDateTime.now()) {
                default_fine = 0;
                next_payment_amount = inputState.getNext_payment_amount();
            }else if(inputState.getNext_payment_date().getDayOfMonth() < LocalDateTime.now().getDayOfMonth()){

                default_fine = inputState.getNext_payment_amount()*0.01;
                next_payment_amount = inputState.getNext_payment_amount()+default_fine;
            }
            PaymentDetailsState paymentDetailsState = new PaymentDetailsState();
            paymentDetailsState.setLoanNumber(paymentDetails.getLoanNumber());
            paymentDetailsState.setAmount_paid(paymentDetails.getAmount_paid());
            paymentDetailsState.setPayment_done_on(LocalDateTime.now());
            paymentDetailsState.setTotal_amount_to_be_paid(inputState.getTotal_amount_to_be_paid());
            paymentDetailsState.setDefault_fine(default_fine);
            paymentDetailsState.setNext_payment_date(next_payment_date);
            paymentDetailsState.setNext_payment_amount(next_payment_amount);
            paymentDetailsState.setLast_payment_date(last_payment_date);
            paymentDetailsState.setContract_enforcement_time(paymentDetails.getContract_enforcement_time());
            paymentDetailsState.setMyParty(inputState.getMyParty());
            paymentDetailsState.setOtherParty(inputState.getOtherParty());

        }
            System.out.println("PaymentDetailsState: "+paymentDetailsState);



            System.out.println("payment to be done : "+payment_to_be_done);

        }

        return null;
    }
}

