package racingcar;

import camp.nextstep.edu.missionutils.Console;
import camp.nextstep.edu.missionutils.Randoms;
import java.util.ArrayList;
import java.util.List;

public class Application {
    public static void main(String[] args) {
        // TODO: 프로그램 구현
        String carNamesInput = View.scanCarNames();
        Validator.validateNameEmpty(carNamesInput);

        String[] names = carNamesInput.split(",");
        List<String> nameList = new ArrayList<>();
        List<Car> cars = new ArrayList<>();
        for (String name : names) {
            String trimName = name.trim(); // 이름 양 끝 공백 제거
            Validator.validateNameLength(trimName);
            Validator.validateNameDuplicate(trimName, nameList);
            nameList.add(trimName);
            cars.add(new Car(trimName, ""));
        }

        String attemptCountInput = View.scanAttemptCount(cars);
        int attemptCount = Validator.validateAndParseInteger(attemptCountInput);
        Validator.validatePositive(attemptCount);

        startRace(cars, attemptCount);

        List<String> winners = getWinners(cars, getMaxDistance(cars));
        View.printWinners(winners);
    }

    static void startRace(List<Car> cars, int attemptCount) {
        for (int i = 0; i < attemptCount; i++) {
            moveCars(cars);
            View.printCarsProgress(cars);
        }
    }

    static void moveCars(List<Car> cars) {
        for (Car car : cars) {
            if (canMove()) {
                car.forward();
            }
        }
    }

    static boolean canMove() {
        return Randoms.pickNumberInRange(0, 9) >= 4;
    }

    static int getMaxDistance(List<Car> cars) {
        int maxDistance = 0;
        for (Car car : cars) {
            maxDistance = Math.max(car.getProgress().length(), maxDistance);
        }
        return maxDistance;
    }

    static List<String> getWinners(List<Car> cars, int maxDistance) {
        List<String> winners = new ArrayList<>();
        for (Car car : cars) {
            if (maxDistance == car.getProgress().length()) {
                winners.add(car.getName());
            }
        }
        return winners;
    }
}
