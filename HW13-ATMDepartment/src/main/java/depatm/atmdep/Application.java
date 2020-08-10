package depatm.atmdep;

import depatm.atm.Atm;

import java.util.ArrayList;
import java.util.List;

/**
 * @author sergey
 * created on 11.09.18.
 */
class Application {
  private final List<String> history = new ArrayList<>();
  private final List<Atm> atmList = new ArrayList<>();

  void addHistoryRecord(String record) {
    history.add(record);
  }

  void addAtmRecord(Atm atm) {
    atmList.add(atm);
  }

  List<Atm> getAtmRecord() {
    return atmList;
  }

  void printHistory() {
    System.out.println(history);
  }

  public void atmCheck() {
    atmList.forEach(atm -> {
      System.out.println(atm.getBalance());
    });

  }
}
